import axios from 'axios';
import React, { useEffect, useState } from 'react';

interface AdminDashboardProps {
    user: any;
    onLogout: () => void;
}

const AdminDashboard: React.FC<AdminDashboardProps> = ({ user, onLogout }) => {
    const [bills, setBills] = useState<any[]>([]);
    const [menuItems, setMenuItems] = useState<any[]>([]);
    const [members, setMembers] = useState<any[]>([]);
    const [loading, setLoading] = useState(true);
    const [activeTab, setActiveTab] = useState<'orders' | 'menu' | 'stats' | 'members'>('orders');

    useEffect(() => {
        const fetchData = async () => {
            try {
                const [billsRes, menuRes, membersRes] = await Promise.all([
                    axios.get('/bill/all'),
                    axios.get('/menu/all'),
                    axios.get('/user/allmember')
                ]);
                setBills(billsRes.data || []);
                setMenuItems(menuRes.data || []);
                setMembers(membersRes.data || []);
            } catch (error) {
                console.error('Failed to fetch admin data', error);
            } finally {
                setLoading(false);
            }
        };
        fetchData();
        const interval = setInterval(fetchData, 5000);
        return () => clearInterval(interval);
    }, []);

    const updateStock = async (menuName: string, newStock: number) => {
        try {
            await axios.patch(`/menu/${menuName}/stock`, { stock: newStock });
            const res = await axios.get('/menu/all');
            setMenuItems(res.data);
        } catch (error) {
            alert('Failed to update stock');
        }
    };

    const updateBillStatus = async (billId: number) => {
        try {
            await axios.patch(`/bill/${billId}/editStatus`);
            const res = await axios.get('/bill/all');
            setBills(res.data);
        } catch (error) {
            alert('Failed to update bill status');
        }
    };

    // Shift-based statistics
    const getShiftStats = () => {
        const now = new Date();
        const currentHour = now.getHours();

        const processOrders = (orders: any[]) => {
            const menuCounts: { [key: string]: number } = {};
            orders.forEach(b => {
                b.orders.forEach((item: any) => {
                    menuCounts[item.menuName] = (menuCounts[item.menuName] || 0) + item.quantity;
                });
            });

            const sales = orders.reduce((acc, curr) => acc + curr.totalPrice, 0);
            
            // Group by category for bar chart
            const categoryStats: { [key: string]: { name: string, count: number }[] } = {
                'APPETIZER': [],
                'MAIN': [],
                'DESSERT': []
            };

            Object.entries(menuCounts).forEach(([name, count]) => {
                const menuItem = menuItems.find(m => m.menuName === name);
                if (menuItem) {
                    const cat = menuItem.courseType.replace('T_', '');
                    if (categoryStats[cat]) {
                        categoryStats[cat].push({ name, count });
                    }
                }
            });

            // Sort and get top items
            Object.keys(categoryStats).forEach(cat => {
                categoryStats[cat].sort((a, b) => b.count - a.count);
            });

            return { count: orders.length, sales, menuCounts, categoryStats };
        };

        const lunchOrders = bills.filter(b => {
            const date = new Date(b.createdAt);
            const hour = date.getHours();
            return hour >= 11 && hour < 15 && date.toDateString() === now.toDateString();
        });
        const dinnerOrders = bills.filter(b => {
            const date = new Date(b.createdAt);
            const hour = date.getHours();
            return hour >= 17 && hour < 23 && date.toDateString() === now.toDateString();
        });

        return {
            lunch: { ...processOrders(lunchOrders), isPast: currentHour >= 15 },
            dinner: { ...processOrders(dinnerOrders), isPast: currentHour >= 23 }
        };
    };

    const stats = getShiftStats();

    const renderBarChart = (categoryStats: any, category: string) => {
        const items = categoryStats[category] || [];
        if (items.length === 0) return null;
        const maxCount = Math.max(...items.map((i: any) => i.count));

        return (
            <div className="bar-chart-container">
                <div className="bar-chart-title">TOP {category}S</div>
                {items.slice(0, 3).map((item: any, idx: number) => (
                    <div key={idx} className="bar-group">
                        <div className="bar-label">
                            <span>{item.name}</span>
                            <span>{item.count} sold</span>
                        </div>
                        <div className="bar-wrapper">
                            <div 
                                className={`bar-fill ${category.toLowerCase()}`} 
                                style={{ width: `${(item.count / maxCount) * 100}%` }}
                            ></div>
                        </div>
                    </div>
                ))}
            </div>
        );
    };

    if (loading) return <div className="admin-loading">INITIALIZING ADMIN PANEL...</div>;

    return (
        <div className="admin-dashboard">
            <header className="admin-header">
                <div className="admin-logo">PERFECT DISH <span className="admin-tag">ADMIN</span></div>
                <nav className="admin-nav">
                    <button className={activeTab === 'orders' ? 'active' : ''} onClick={() => setActiveTab('orders')}>ORDERS</button>
                    <button className={activeTab === 'menu' ? 'active' : ''} onClick={() => setActiveTab('menu')}>MENU & STOCK</button>
                    <button className={activeTab === 'members' ? 'active' : ''} onClick={() => setActiveTab('members')}>MEMBERS</button>
                    <button className={activeTab === 'stats' ? 'active' : ''} onClick={() => setActiveTab('stats')}>STATISTICS</button>
                </nav>
                <div className="admin-user-info">
                    <span>{user.phoneNumber}</span>
                    <button className="admin-logout-btn" onClick={onLogout}>LOGOUT</button>
                </div>
            </header>

            <div className="admin-content">
                {activeTab === 'orders' && (
                    <section className="admin-section">
                        <h2>INCOMING ORDERS (GROUPED BILLS)</h2>
                        <div className="bill-grid">
                            {bills.map(bill => (
                                <div key={bill.id} className="bill-card">
                                    <div className="bill-header">
                                        <div className={`bill-no ${bill.billStatus === 'SERVED' ? 'served' : ''}`}>
                                            BILL NO. {bill.id}
                                        </div>
                                        <div className="bill-date">{new Date(bill.createdAt).toLocaleTimeString()}</div>
                                    </div>
                                    <div className="bill-divider"></div>
                                    <div className="bill-customer">
                                        <label>TABLE:</label>
                                        <span>{bill.tableNo}</span>
                                    </div>
                                    <div className="bill-items">
                                        {bill.orders.map((item: any, idx: number) => (
                                            <div key={idx} className="bill-item">
                                                <span className="item-name">{item.menuName}</span>
                                                <span className="item-qty">x{item.quantity}</span>
                                                <span className="item-price">${item.price * item.quantity}</span>
                                            </div>
                                        ))}
                                    </div>
                                    <div className="bill-divider"></div>
                                    <div className="bill-total" style={{ display: 'flex', justifyContent: 'space-between', fontWeight: 'bold', margin: '0.5rem 0' }}>
                                        <span>TOTAL</span>
                                        <span>${bill.totalPrice}</span>
                                    </div>
                                    <div className="bill-footer">
                                        <div className="bill-status">
                                            <span className={`status-badge ${bill.billStatus.toLowerCase()}`}>{bill.billStatus}</span>
                                        </div>
                                        {bill.billStatus === 'NOTSERVED' && (
                                            <button 
                                                onClick={() => updateBillStatus(bill.id)}
                                                className="cta-button primary"
                                                style={{ padding: '0.3rem 0.6rem', fontSize: '0.8rem' }}
                                            >
                                                COMPLETE ALL
                                            </button>
                                        )}
                                    </div>
                                </div>
                            ))}
                        </div>
                    </section>
                )}

                {activeTab === 'menu' && (
                    <section className="admin-section">
                        <h2>MENU & INVENTORY</h2>
                        <div className="admin-grid">
                            {menuItems.map(item => (
                                <div key={item.menuName} className="admin-menu-card">
                                    <div className="admin-menu-info">
                                        <h3>{item.menuName}</h3>
                                        <p>{item.courseType.replace('T_', '')}</p>
                                        <div className="stock-info">
                                            STOCK: <input 
                                                type="number" 
                                                value={item.stock} 
                                                onChange={(e) => updateStock(item.menuName, parseInt(e.target.value))}
                                                className="stock-input"
                                            />
                                        </div>
                                    </div>
                                    <div className="admin-menu-actions">
                                        <span className={`availability-dot ${item.availability === 'AVAILABLE' ? 'available' : 'unavailable'}`}></span>
                                        <span className="availability-text">{item.availability}</span>
                                    </div>
                                </div>
                            ))}
                        </div>
                    </section>
                )}

                {activeTab === 'members' && (
                    <section className="admin-section">
                        <h2>MEMBER MANAGEMENT</h2>
                        <div className="admin-table-container">
                            <table className="admin-table">
                                <thead>
                                    <tr>
                                        <th>NAME</th>
                                        <th>PHONE</th>
                                        <th>JOINED</th>
                                        <th>STATUS</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {members.map(member => (
                                        <tr key={member.phoneNumber}>
                                            <td>{member.userName}</td>
                                            <td>{member.phoneNumber}</td>
                                            <td>{member.createdAt}</td>
                                            <td><span className={`status-badge active`}>{member.status}</span></td>
                                        </tr>
                                    ))}
                                </tbody>
                            </table>
                        </div>
                    </section>
                )}

                {activeTab === 'stats' && (
                    <section className="admin-section">
                        <h2>SALES STATISTICS</h2>
                        <div className="stats-grid">
                            <div className="stats-card lunch">
                                <div className="stats-icon">☀️</div>
                                <h3>LUNCH PART</h3>
                                <p className="stats-time">11:00 - 15:00</p>
                                <div className="stats-data">
                                    <div className="data-item">
                                        <label>ORDERS</label>
                                        <span>{stats.lunch.count}</span>
                                    </div>
                                    <div className="data-item">
                                        <label>SALES</label>
                                        <span>${stats.lunch.sales.toLocaleString()}</span>
                                    </div>
                                </div>
                                <div className="stats-menu-list">
                                    {Object.entries(stats.lunch.menuCounts).map(([name, count]) => (
                                        <div key={name} className="stats-menu-item">
                                            <span>{name}</span>
                                            <span>{count as number}</span>
                                        </div>
                                    ))}
                                </div>
                                {stats.lunch.isPast && (
                                    <>
                                        {renderBarChart(stats.lunch.categoryStats, 'APPETIZER')}
                                        {renderBarChart(stats.lunch.categoryStats, 'MAIN')}
                                        {renderBarChart(stats.lunch.categoryStats, 'DESSERT')}
                                    </>
                                )}
                            </div>
                            <div className="stats-card dinner">
                                <div className="stats-icon">🌙</div>
                                <h3>DINNER PART</h3>
                                <p className="stats-time">17:00 - 23:00</p>
                                <div className="stats-data">
                                    <div className="data-item">
                                        <label>ORDERS</label>
                                        <span>{stats.dinner.count}</span>
                                    </div>
                                    <div className="data-item">
                                        <label>SALES</label>
                                        <span>${stats.dinner.sales.toLocaleString()}</span>
                                    </div>
                                </div>
                                <div className="stats-menu-list">
                                    {Object.entries(stats.dinner.menuCounts).map(([name, count]) => (
                                        <div key={name} className="stats-menu-item">
                                            <span>{name}</span>
                                            <span>{count as number}</span>
                                        </div>
                                    ))}
                                </div>
                                {stats.dinner.isPast && (
                                    <>
                                        {renderBarChart(stats.dinner.categoryStats, 'APPETIZER')}
                                        {renderBarChart(stats.dinner.categoryStats, 'MAIN')}
                                        {renderBarChart(stats.dinner.categoryStats, 'DESSERT')}
                                    </>
                                )}
                            </div>
                        </div>
                    </section>
                )}
            </div>
        </div>
    );
};

export default AdminDashboard;

import axios from 'axios';
import React, { useEffect, useState } from 'react';

interface OrderItem {
    tableNo: string;
    phoneNumber: string;
    menuName: string;
    quantity: number;
    price: number;
    totalPrice: number;
    orderStatus: string;
}

interface OrderHistoryProps {
    user: any;
    onBack: () => void;
}

const OrderHistory: React.FC<OrderHistoryProps> = ({ user, onBack }) => {
    const [orders, setOrders] = useState<OrderItem[]>([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchOrders = async () => {
            try {
                const response = await axios.get(`/order/my/${user.phoneNumber}`);
                setOrders(response.data);
            } catch (error) {
                console.error('Failed to fetch order history', error);
            } finally {
                setLoading(false);
            }
        };
        fetchOrders();
        const interval = setInterval(fetchOrders, 5000); // Poll every 5 seconds
        return () => clearInterval(interval);
    }, [user.phoneNumber]);

    const groupedOrders = orders.reduce((acc: any, order: any) => {
        const billId = order.billId || 'unknown';
        if (!acc[billId]) {
            acc[billId] = {
                billId,
                tableNo: order.tableNo,
                orderStatus: order.orderStatus,
                createdAt: order.createdAt,
                items: [],
                total: 0
            };
        }
        acc[billId].items.push(order);
        acc[billId].total += order.totalPrice;
        return acc;
    }, {});

    const sortedBills = Object.values(groupedOrders).sort((a: any, b: any) => 
        new Date(b.createdAt).getTime() - new Date(a.createdAt).getTime()
    );

    if (loading) return <div className="sticker" style={{ margin: '4rem auto', display: 'block', width: 'fit-content' }}>FETCHING YOUR MEMORIES...</div>;

    return (
        <div className="order-history-container">
            <div style={{ display: 'flex', alignItems: 'center', gap: '1rem', marginBottom: '2rem' }}>
                <button className="cta-button secondary" onClick={onBack}>← BACK</button>
                <h2 style={{ margin: 0, color: 'var(--primary)', fontSize: '2.5rem' }}>MY ORDERS</h2>
            </div>

            {sortedBills.length === 0 ? (
                <div className="kitsch-card" style={{ textAlign: 'center', padding: '4rem' }}>
                    <div style={{ fontSize: '4rem', marginBottom: '1rem' }}>🏜️</div>
                    <h3>NO ORDERS YET!</h3>
                    <p>GO GRAB SOME YUMMY FOOD!</p>
                </div>
            ) : (
                <div className="orders-list">
                    {sortedBills.map((bill: any, index) => (
                        <div key={bill.billId} className="kitsch-card order-history-item">
                            <div className="order-header">
                                <span className="sticker" style={{ background: 'var(--secondary)' }}>ORDER #{sortedBills.length - index}</span>
                                <span className={`status-badge ${bill.orderStatus.toLowerCase()}`}>{bill.orderStatus}</span>
                                <span style={{ fontSize: '0.8rem', opacity: 0.7 }}>{new Date(bill.createdAt).toLocaleString()}</span>
                            </div>
                            <div className="order-body">
                                <div className="items-list" style={{ width: '100%' }}>
                                    {bill.items.map((item: any, idx: number) => (
                                        <div key={idx} style={{ display: 'flex', justifyContent: 'space-between', margin: '0.5rem 0' }}>
                                            <span>{item.menuName} x {item.quantity}</span>
                                            <span>${item.totalPrice}</span>
                                        </div>
                                    ))}
                                    <div style={{ borderTop: '2px dashed #000', marginTop: '1rem', paddingTop: '0.5rem', display: 'flex', justifyContent: 'space-between', fontWeight: 'bold' }}>
                                        <span>TOTAL</span>
                                        <span>${bill.total}</span>
                                    </div>
                                </div>
                                <div className="table-info" style={{ marginTop: '1rem' }}>
                                    <label>TABLE: </label>
                                    <span>{bill.tableNo}</span>
                                </div>
                            </div>
                        </div>
                    ))}
                </div>
            )}
        </div>
    );
};

export default OrderHistory;

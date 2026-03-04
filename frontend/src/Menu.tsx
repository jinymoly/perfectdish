import axios from 'axios';
import React, { useEffect, useState } from 'react';

interface MenuItem {
    menuName: string;
    price: number;
    courseType: string;
    description: string;
    menuImgUrl: string;
    availability: string;
}

interface MenuProps {
    onAddToOrder: (item: MenuItem) => void;
}

const CATEGORIES = [
    { id: 'ALL', label: 'ALL' },
    { id: 'T_APPETIZER', label: 'APPETIZER' },
    { id: 'T_MAIN', label: 'MAIN' },
    { id: 'T_DESSERT', label: 'DESSERT' },
];

const Menu: React.FC<MenuProps> = ({ onAddToOrder }) => {
    const [items, setItems] = useState<MenuItem[]>([]);
    const [filteredItems, setFilteredItems] = useState<MenuItem[]>([]);
    const [activeCategory, setActiveCategory] = useState('ALL');
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchMenu = async () => {
            try {
                const response = await axios.get('/menu/available');
                setItems(response.data);
                // Only update filtered items if we are in 'ALL' or if the items changed
                // Actually, the secondary useEffect handles filtering based on 'items'
            } catch (error) {
                console.error('Failed to fetch menu', error);
            } finally {
                setLoading(false);
            }
        };
        fetchMenu();
        const interval = setInterval(fetchMenu, 10000); // Poll every 10 seconds
        return () => clearInterval(interval);
    }, []);

    useEffect(() => {
        if (activeCategory === 'ALL') {
            setFilteredItems(items);
        } else {
            setFilteredItems(items.filter(item => item.courseType === activeCategory));
        }
    }, [activeCategory, items]);

    if (loading) return <div className="sticker" style={{ margin: '4rem auto', display: 'block', width: 'fit-content' }}>LOADING YUMMY FOOD...</div>;

    return (
        <div className="menu-container">
            <div className="category-tabs">
                {CATEGORIES.map(cat => (
                    <button 
                        key={cat.id}
                        className={`category-btn ${activeCategory === cat.id ? 'active' : ''}`}
                        onClick={() => setActiveCategory(cat.id)}
                    >
                        {cat.label}
                    </button>
                ))}
            </div>

            <div className="menu-grid">
                {filteredItems.map((item) => (
                    <div key={item.menuName} className="kitsch-card menu-item">
                        <div className="menu-image">
                            {item.menuImgUrl ? (
                                <img src={item.menuImgUrl} alt={item.menuName} style={{ width: '100%', height: '100%', objectFit: 'cover' }} />
                            ) : (
                                <div className="placeholder-image">🍔</div>
                            )}
                        </div>
                        <div className="menu-info">
                            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '0.5rem' }}>
                                <div className="sticker" style={{ background: 'var(--accent)', fontSize: '0.7rem' }}>
                                    {item.courseType.replace('T_', '')}
                                </div>
                                <div className="availability-emoji" title={item.availability === 'AVAILABLE' ? 'Available' : 'Sold Out'}>
                                    {item.availability === 'AVAILABLE' ? '✅' : '❌'}
                                </div>
                            </div>
                            <h3>{item.menuName}</h3>
                            <p className="menu-description">{item.description}</p>
                            <div className="price-tag">
                                ${item.price.toLocaleString()}
                            </div>
                            <button 
                                className="cta-button primary" 
                                style={{ width: '100%', marginTop: '1rem', padding: '0.8rem', fontSize: '1.1rem' }}
                                onClick={() => onAddToOrder(item)}
                                disabled={item.availability !== 'AVAILABLE'}
                            >
                                {item.availability === 'AVAILABLE' ? 'ADD TO TRAY!' : 'SOLD OUT'}
                            </button>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default Menu;

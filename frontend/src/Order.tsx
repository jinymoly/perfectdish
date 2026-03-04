import React from 'react';

interface MenuItem {
    menuName: string;
    price: number;
}

interface OrderItem extends MenuItem {
    quantity: number;
}

interface OrderProps {
    items: OrderItem[];
    onRemove: (name: string) => void;
    onPlaceOrder: () => void;
}

const Order: React.FC<OrderProps> = ({ items, onRemove, onPlaceOrder }) => {
    const total = items.reduce((sum, item) => sum + (item.price * item.quantity), 0);

    return (
        <div className="kitsch-card order-tray">
            <div className="sticker" style={{ position: 'absolute', top: '-20px', left: '20px' }}>YOUR TRAY</div>
            {items.length === 0 ? (
                <p style={{ textAlign: 'center', padding: '2rem' }}>YOUR TRAY IS EMPTY! GO GET SOME FOOD!</p>
            ) : (
                <>
                    <div className="order-items">
                        {items.map((item) => (
                            <div key={item.menuName} className="order-item-row">
                                <span className="item-name">{item.menuName} x {item.quantity}</span>
                                <span className="item-price">${item.price * item.quantity}</span>
                                <button className="remove-btn" onClick={() => onRemove(item.menuName)}>X</button>
                            </div>
                        ))}
                    </div>
                    <div className="order-total">
                        <span>TOTAL:</span>
                        <span className="total-amount">${total}</span>
                    </div>
                    <button className="cta-button primary" style={{ width: '100%', marginTop: '2rem' }} onClick={onPlaceOrder}>
                        CHECKOUT NOW!
                    </button>
                </>
            )}
        </div>
    );
};

export default Order;

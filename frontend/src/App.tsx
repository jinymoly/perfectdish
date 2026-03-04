import axios from 'axios';
import { useEffect, useState } from 'react';
import AdminDashboard from './AdminDashboard';
import './App.css';
import Login from './Login';
import Menu from './Menu';
import Order from './Order';
import OrderHistory from './OrderHistory';
import Signup from './Signup';

type ViewState = 'home' | 'login' | 'signup' | 'kiosk' | 'bill' | 'admin' | 'history';

interface MenuItem {
  menuName: string;
  price: number;
  courseType: string;
  description: string;
  menuImgUrl: string;
  availability: string;
}

interface OrderItem extends MenuItem {
  quantity: number;
}

function App() {
  const [view, setView] = useState<ViewState>('home');
  const [user, setUser] = useState<any>(null);
  const [orderItems, setOrderItems] = useState<OrderItem[]>([]);
  const [pendingItem, setPendingItem] = useState<MenuItem | null>(null);
  const [tableNo, setTableNo] = useState<string>('T1');
  const [lastOrder, setLastOrder] = useState<{ items: OrderItem[], total: number } | null>(null);

  useEffect(() => {
    const savedUser = localStorage.getItem('user');
    if (savedUser) {
      const userData = JSON.parse(savedUser);
      setUser(userData);
      if (userData.role === 'ADMIN') {
        setView('admin');
      } else {
        setView('kiosk');
      }
    }
  }, []);


  const handleLoginSuccess = (userData: any) => {
    console.log('Login Success Data:', userData);
    setUser(userData);
    localStorage.setItem('user', JSON.stringify(userData));
    if (userData.role === 'ADMIN') {
      setView('admin');
    } else {
      if (pendingItem) {
        addToOrder(pendingItem);
        setPendingItem(null);
      }
      setView('kiosk');
    }
  };

  const handleLogout = () => {
    setUser(null);
    localStorage.removeItem('user');
    setOrderItems([]);
    setView('home');
  };

  const addToOrder = (item: MenuItem) => {
    if (!user) {
      setPendingItem(item);
      setView('login');
      return;
    }

    setOrderItems(prev => {
      const existing = prev.find(i => i.menuName === item.menuName);
      if (existing) {
        return prev.map(i => i.menuName === item.menuName ? { ...i, quantity: i.quantity + 1 } : i);
      }
      return [...prev, { ...item, quantity: 1 }];
    });
  };

  const removeFromOrder = (name: string) => {
    setOrderItems(prev => prev.filter(i => i.menuName !== name));
  };

  const placeOrder = async () => {
    if (!user || orderItems.length === 0) return;

    try {
      // In a real scenario, we might send multiple items or one by one. 
      // The current backend createOrder takes a single OrderRequest.
      // We'll send them sequentially for now or update backend later.
      // For this task, let's send the first one as a representative or loop.
      const batchData = {
        tableNo: tableNo,
        phoneNumber: user.phoneNumber,
        items: orderItems.map(item => ({
          menuName: item.menuName,
          quantity: item.quantity
        }))
      };

      await axios.post('/order/batch', batchData);

      setLastOrder({
        items: [...orderItems],
        total: orderItems.reduce((sum, i) => sum + (i.price * i.quantity), 0)
      });
      setOrderItems([]);
      setView('bill');
    } catch (error: any) {
      console.error('Order failed', error);
      const errorMsg = error.response?.data?.errorMessage || error.message;
      alert(`ORDER FAILED! ${errorMsg}`);
    }
  };

  if (view === 'admin' && user?.role === 'ADMIN') {
    return <AdminDashboard user={user} onLogout={handleLogout} />;
  }

  return (
    <div className="app-container">
      <nav className="navbar kitsch-card" style={{ padding: '0.5rem 2rem', borderRadius: '0' }}>
        <div className="logo" onClick={() => setView('home')}>PERFECT DISH</div>
        <div className="nav-links">
          <button className="nav-btn" onClick={() => setView('kiosk')}>MENU</button>
          {user ? (
            <>
              <div className="sticker" onClick={() => setView('history')} style={{ cursor: 'pointer', background: 'var(--secondary)', color: 'white' }}>
                Welcome! {user.name}님
              </div>
              <div className="nav-item" style={{ display: 'flex', alignItems: 'center', gap: '0.5rem', marginLeft: '1rem' }}>
                <label style={{ fontWeight: 'bold', fontSize: '0.8rem' }}>TABLE:</label>
                <input 
                  type="text" 
                  value={tableNo} 
                  onChange={(e) => setTableNo(e.target.value)}
                  style={{ width: '50px', padding: '0.2rem', border: '2px solid #000', borderRadius: '4px', fontWeight: 'bold', textAlign: 'center' }}
                />
              </div>
              <button className="nav-btn" onClick={handleLogout}>LOGOUT</button>
              {orderItems.length > 0 && (
                <div className="ordering-badge">주문 중... 🛒</div>
              )}
            </>
          ) : (
            <>
              <button className="nav-btn" onClick={() => setView('login')}>LOGIN</button>
              <button className="nav-btn primary" onClick={() => setView('signup')}>JOIN</button>
            </>
          )}
        </div>
      </nav>

      <main className="content" style={{ width: '100%', display: 'flex', justifyContent: 'center' }}>
        {view === 'home' && (
          <div className="hero">
            <h1 className="hero-title">
              THE <span className="highlight">BEST</span> DISH <br/> 
              IN THE <span className="highlight">WORLD!</span>
            </h1>
            <div className="hero-subtitle">
              FAST! TASTY! YUMMY!
            </div>
            <div className="hero-actions">
              <button className="cta-button primary" onClick={() => setView('signup')}>
                START NOW!
              </button>
              <button className="cta-button secondary" onClick={() => setView('kiosk')}>
                SEE MENU
              </button>
            </div>
          </div>
        )}

        {view === 'login' && (
          <div style={{ textAlign: 'center' }}>
            <Login onSuccess={handleLoginSuccess} />
            <button className="nav-btn" style={{ marginTop: '2rem' }} onClick={() => setView('kiosk')}>BACK TO MENU</button>
          </div>
        )}
        
        {view === 'signup' && (
          <div style={{ textAlign: 'center' }}>
            <Signup />
            <button className="nav-btn" style={{ marginTop: '2rem' }} onClick={() => setView('kiosk')}>BACK TO MENU</button>
          </div>
        )}
        
        {view === 'kiosk' && (
          <div style={{ display: 'flex', gap: '3rem', width: '100%', maxWidth: '1200px', alignItems: 'flex-start' }}>
            <Menu onAddToOrder={addToOrder} />
            <Order items={orderItems} onRemove={removeFromOrder} onPlaceOrder={placeOrder} />
          </div>
        )}

        {view === 'history' && user && (
          <OrderHistory user={user} onBack={() => setView('kiosk')} />
        )}

        {view === 'bill' && lastOrder && (
          <div className="kitsch-card" style={{ maxWidth: '600px', textAlign: 'center' }}>
            <h2 style={{ fontSize: '3rem', color: 'var(--primary)' }}>THANK YOU!</h2>
            <p className="subtitle">YOUR ORDER IS BEING PREPARED!</p>
            
            <div className="order-summary kitsch-card" style={{ background: 'white', margin: '2rem 0', textAlign: 'left', padding: '1.5rem' }}>
              <h3 style={{ borderBottom: '2px dashed var(--text-dark)', paddingBottom: '0.5rem' }}>ORDER SUMMARY</h3>
              {lastOrder.items.map((item, idx) => (
                <div key={idx} style={{ display: 'flex', justifyContent: 'space-between', margin: '0.5rem 0' }}>
                  <span>{item.menuName} x {item.quantity}</span>
                  <span>${item.price * item.quantity}</span>
                </div>
              ))}
              <div style={{ borderTop: '2px solid var(--text-dark)', marginTop: '1rem', paddingTop: '1rem', display: 'flex', justifyContent: 'space-between', fontWeight: '900', fontSize: '1.5rem' }}>
                <span>TOTAL</span>
                <span>${lastOrder.total}</span>
              </div>
            </div>

            <div className="sticker" style={{ fontSize: '3rem', margin: '2rem 0', background: 'var(--primary)', color: 'white' }}>#001</div>
            <button className="cta-button primary" onClick={() => { setLastOrder(null); setView('kiosk'); }}>
              ORDER MORE!
            </button>
          </div>
        )}
      </main>

      {view === 'home' && (
        <section className="features">
          <div className="kitsch-card feature-card">
            <span className="feature-icon">🚀</span>
            <h3>SUPER FAST</h3>
            <p>NO WAITING! JUST EATING!</p>
          </div>
          <div className="kitsch-card feature-card" style={{ boxShadow: '12px 12px 0 var(--accent)' }}>
            <span className="feature-icon">💎</span>
            <h3>PREMIUM</h3>
            <p>ONLY THE BEST FOR YOU!</p>
          </div>
          <div className="kitsch-card feature-card" style={{ boxShadow: '12px 12px 0 var(--secondary)' }}>
            <span className="feature-icon">🌈</span>
            <h3>VIBRANT</h3>
            <p>LIFE IS TOO SHORT FOR BORING FOOD!</p>
          </div>
        </section>
      )}
    </div>
  )
}

export default App

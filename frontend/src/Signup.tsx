import axios from 'axios';
import React, { useState } from 'react';

const Signup: React.FC = () => {
    const [formData, setFormData] = useState({
        userName: '',
        phoneNumber: '',
        password: ''
    });
    const [message, setMessage] = useState('');
    const [loading, setLoading] = useState(false);

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value
        });
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setLoading(true);
        setMessage('');
        try {
            await axios.post('/user/join', formData);
            setMessage(`YES! WELCOME, ${formData.userName.toUpperCase()}!`);
        } catch (error: any) {
            setMessage(`OH NO! ${error.response?.data?.errorMessage || error.message}`);
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="kitsch-card auth-card">
            <div className="sticker" style={{ position: 'absolute', top: '-20px', right: '-20px' }}>NEW!</div>
            <h2>JOIN THE CLUB!</h2>
            <p className="subtitle">GET READY FOR THE BEST DISH EVER!</p>
            
            <form onSubmit={handleSubmit}>
                <div className="input-group">
                    <label>YOUR NAME</label>
                    <input 
                        type="text" 
                        name="userName" 
                        value={formData.userName} 
                        onChange={handleChange} 
                        placeholder="COOL PERSON"
                        required 
                    />
                </div>
                <div className="input-group">
                    <label>PHONE (8 DIGITS)</label>
                    <input 
                        type="text" 
                        name="phoneNumber" 
                        value={formData.phoneNumber} 
                        onChange={handleChange} 
                        placeholder="12345678"
                        required 
                        pattern="[0-9]{8}"
                    />
                </div>
                <div className="input-group">
                    <label>PASSWORD</label>
                    <input 
                        type="password" 
                        name="password" 
                        value={formData.password} 
                        onChange={handleChange} 
                        placeholder="••••••••"
                        required 
                    />
                </div>
                <button type="submit" className="submit-btn" disabled={loading}>
                    {loading ? 'WAITING...' : 'JOIN NOW!'}
                </button>
            </form>
            {message && (
                <div className={`message ${message.includes('OH NO') ? 'error' : 'success'}`} 
                     style={{ border: '3px solid #000', fontWeight: '900', marginTop: '2rem' }}>
                    {message}
                </div>
            )}
        </div>
    );
};

export default Signup;

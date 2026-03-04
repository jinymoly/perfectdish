import axios from 'axios';
import React, { useState } from 'react';

interface LoginProps {
    onSuccess: (data: any) => void;
}

const Login: React.FC<LoginProps> = ({ onSuccess }) => {
    const [formData, setFormData] = useState({
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
            const response = await axios.post('/user/login', formData);
            setMessage('WELCOME BACK!');
            onSuccess(response.data);
        } catch (error: any) {
            setMessage(`OOPS! ${error.response?.data?.errorMessage || error.message}`);
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="kitsch-card auth-card">
            <div className="sticker" style={{ position: 'absolute', top: '-20px', left: '-20px', background: 'var(--secondary)' }}>HELLO!</div>
            <h2>SIGN IN!</h2>
            <p className="subtitle">WE MISSED YOU SO MUCH!</p>
            
            <form onSubmit={handleSubmit}>
                <div className="input-group">
                    <label>PHONE NUMBER</label>
                    <input 
                        type="text" 
                        name="phoneNumber" 
                        value={formData.phoneNumber} 
                        onChange={handleChange} 
                        placeholder="ID or PHONE"
                        required 
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
                <button type="submit" className="submit-btn" disabled={loading} style={{ background: 'var(--secondary)' }}>
                    {loading ? 'LOADING...' : 'LET\'S GO!'}
                </button>
            </form>
            {message && (
                <div className={`message ${message.includes('OOPS') ? 'error' : 'success'}`}
                     style={{ border: '3px solid #000', fontWeight: '900', marginTop: '2rem' }}>
                    {message}
                </div>
            )}
        </div>
    );
};

export default Login;

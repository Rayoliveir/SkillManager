import React, { createContext, useState, useContext, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import * as api from '../services/api';

const AuthContext = createContext(null);

export const AuthProvider = ({ children }) => {
    const [user, setUser] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        // Check if user is already logged in
        const token = localStorage.getItem('authToken');
        const username = localStorage.getItem('username');
        const roles = localStorage.getItem('roles');
        
        if (token && username && roles) {
            try {
                const parsedRoles = JSON.parse(roles);
                const userData = {
                    username: username,
                    roles: parsedRoles,
                    isAuthenticated: true
                };
                setUser(userData);
            } catch (error) {
                console.error("Error parsing user data:", error);
                handleLogout();
            }
        }
    }, []);

    const handleLogin = async (username, password) => {
        try {
            // Production login flow
            const data = await api.login(username, password);
            
            const userData = {
                username: data.username,
                roles: data.roles || [],
                isAuthenticated: true
            };

            setUser(userData);
            localStorage.setItem('user', JSON.stringify(userData));

            // Redirect based on user role
            if (data.roles && data.roles.length > 0) {
                const role = data.roles[0];
                if (role.includes('ESTAGIARIO')) {
                    navigate('/dashboardEstagiario');
                } else if (role.includes('FACULDADE')) {
                    navigate('/dashboardFaculdade');
                } else if (role.includes('SUPERVISOR') || role.includes('GERENTE')) {
                    navigate('/dashboardFuncionario');
                } else {
                    navigate('/dashboard');
                }
            } else {
                navigate('/dashboard');
            }
        } catch (error) {
            console.error("Erro no login:", error);
            handleLogout();
            throw error;
        }
    };

    const handleLogout = () => {
        api.logout();
        localStorage.removeItem('authToken');
        localStorage.removeItem('username');
        localStorage.removeItem('roles');
        localStorage.removeItem('user');
        setUser(null);
        navigate('/login');
    };

    const value = {
        user,
        isAuthenticated: !!user,
        login: handleLogin,
        logout: handleLogout,
    };

    return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
};

export const useAuth = () => {
    return useContext(AuthContext);
};
import React, { createContext, useState, useContext, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import * as api from '../services/api';

const AuthContext = createContext(null);

export const AuthProvider = ({ children }) => {
    const [user, setUser] = useState(null);
    const [loading, setLoading] = useState(true); // --- NOVO: Estado de loading
    const navigate = useNavigate();

    useEffect(() => {
        // --- MUDANÇA: Remove o 'dev mode'
        // Ao carregar a app, tenta carregar o utilizador do localStorage
        try {
            const storedUser = localStorage.getItem('user');
            if (storedUser) {
                const userData = JSON.parse(storedUser);
                // Define o utilizador no estado, se ele existir no localStorage
                setUser({ ...userData, isAuthenticated: true });
            }
        } catch (error) {
            console.error("Erro ao carregar dados do utilizador:", error);
            api.logout(); // Limpa o localStorage se estiver corrompido
        }
        setLoading(false); // Termina o loading
    }, []);

    const handleLogin = async (username, password) => {
        try {
            // --- MUDANÇA: Chama a nova 'api.login' de 'services/api.js'
            // A 'api.login' já guarda o token e o 'user' no localStorage
            const data = await api.login(username, password);
            
            const userData = {
                username: data.username,
                roles: data.roles || [],
                isAuthenticated: true
            };

            setUser(userData); // Atualiza o estado global
            
            // --- MUDANÇA: Redirecionamento unificado para /dashboard
            // O nosso novo 'components/DashboardRouter.js' vai tratar o resto.
            navigate('/dashboard');

        } catch (error) {
            console.error("Erro no login:", error);
            // Re-lança o erro para que o componente de Login ('components/login/index.js') o apanhe
            // e mostre a mensagem (ex: "Email ou senha inválidos.")
            throw error; 
        }
    };

    const handleLogout = () => {
        api.logout(); // --- MUDANÇA: Chama a nova 'api.logout'
        setUser(null);
        navigate('/login');
    };

    // Não renderiza a app até sabermos se o user está logado ou não
    if (loading) {
        return null; // ou um <LoadingSpinner />
    }

    const value = {
        user, // O objeto do utilizador (ou null)
        isAuthenticated: !!user, // true ou false
        login: handleLogin,
        logout: handleLogout,
    };

    return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
};

export const useAuth = () => {
    return useContext(AuthContext);
};
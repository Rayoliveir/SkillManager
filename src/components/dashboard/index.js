import React, { useState, useEffect } from 'react';
import { useAuth } from '../../context/AuthContext';
import * as api from '../../services/api';

function Dashboard() {
    const { user, logout } = useAuth();
    const [dashboardData, setDashboardData] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');

    useEffect(() => {
        const fetchData = async () => {
            if (!user || !user.roles) {
                setLoading(false);
                return;
            };

            try {
                setLoading(true);
                let data;
                if (user.roles.includes('ROLE_SUPERVISOR') || user.roles.includes('ROLE_ADMIN')) {
                    data = await api.getSupervisorDashboard();
                } else if (user.roles.includes('ROLE_FACULDADE')) {
                    data = await api.getFaculdadeDashboard();
                } else if (user.roles.includes('ROLE_ESTAGIARIO')) {
                    data = await api.getEstagiarioDashboard();
                }
                setDashboardData(data);
            } catch (err) {
                setError('Falha ao carregar os dados do dashboard.');
                console.error(err);
            } finally {
                setLoading(false);
            }
        };

        fetchData();
    }, [user]);

    if (loading) {
        return <div>Carregando...</div>;
    }

    if (error) {
        return <div>{error} <button onClick={logout}>Sair</button></div>;
    }

    if (user && dashboardData) {
        if (user.roles.includes('ROLE_SUPERVISOR') || user.roles.includes('ROLE_ADMIN')) {
            return (
                <div>
                    <h1>Dashboard do supervisor de estágio</h1>
                    <pre>{JSON.stringify(dashboardData, null, 2)}</pre>
                    <button onClick={logout}>Sair</button>
                </div>
            );
        }
        if (user.roles.includes('ROLE_FACULDADE')) {
            return (
                <div>
                    <h1>Dashboard do coordenador de estágio</h1>
                    <pre>{JSON.stringify(dashboardData, null, 2)}</pre>
                    <button onClick={logout}>Sair</button>
                </div>
            );
        }
        if (user.roles.includes('ROLE_ESTAGIARIO')) {
            return (
                <div>
                    <h1>Dashboard do estagiário</h1>
                    <pre>{JSON.stringify(dashboardData, null, 2)}</pre>
                    <button onClick={logout}>Sair</button>
                </div>
            );
        }
    }

    return <div>Nenhum dashboard disponível para seu perfil. <button onClick={logout}>Sair</button></div>;
}

export default Dashboard;
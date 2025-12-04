import React from 'react';
import { useAuth } from '../context/AuthContext';

// --- NOVO: Importa as *páginas* (ou componentes) do dashboard ---
// (Verifique se estes caminhos para as suas 'Telas' estão corretos)
import TelaDashboardFuncionario from '../pages/TelaDashboardFuncionario';
import TelaDashboardFaculdade from '../pages/TelaDashboardFaculdade';
import TelaDashboardEstagiario from '../pages/TelaDashboardEstagiario';
// import TelaDashboardAdmin from '../pages/TelaDashboardAdmin'; // Adicione se tiver um

const DashboardRouter = () => {
    // --- NOVO: Este componente lê o utilizador do 'context/AuthContext.js' ---
    const { user } = useAuth();

    if (!user || !user.roles || user.roles.length === 0) {
        // Se algo falhar (sem utilizador ou role), mostra um loading ou erro
        return <div>Carregando...</div>;
    }

    // --- NOVO: Lógica que decide qual dashboard mostrar ---
    const primaryRole = user.roles[0]; // Pega a primeira role (ex: "ROLE_ESTAGIARIO")

    if (primaryRole.includes('ESTAGIARIO')) {
        return <TelaDashboardEstagiario />;
    }
    
    if (primaryRole.includes('FACULDADE')) {
        return <TelaDashboardFaculdade />;
    }
    
    if (primaryRole.includes('SUPERVISOR') || primaryRole.includes('GERENTE')) {
        return <TelaDashboardFuncionario />;
    }
    
    if (primaryRole.includes('ADMIN')) {
        // return <TelaDashboardAdmin />; // Se tiver um dashboard de Admin
        return <div>Dashboard de Admin (Implementar)</div>;
    }

    // Fallback caso a role não seja reconhecida
    return <div>Tipo de utilizador desconhecido.</div>;
};

export default DashboardRouter;
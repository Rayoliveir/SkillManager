import React from 'react';
import { useAuth } from '../../context/AuthContext';
import { LogOut } from 'lucide-react';
import './styles.css';

function HeaderDashboard() {
  const { logout, user } = useAuth();

  return (
    <header className="hd-header">
      <div className="hd-left">
        <img src="/assets/images/logoSemTexto.png" alt="logo" className="hd-logo" />
        <div className="hd-title">
          <h1>SKILLMANAGER</h1>
          <span>Acompanhe, Avalie, Evolua</span>
        </div>
      </div>
      <div className="hd-right">
        <div className="hd-user">
          <span className="hd-username">{user?.nome ?? user?.username ?? 'Usuário'}</span>
        </div>
        <button className="hd-logout" onClick={logout}>
          <LogOut size={16} />
          <span>Sair</span>
        </button>
      </div>
    </header>
  );
}

export default HeaderDashboard;
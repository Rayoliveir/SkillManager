import React, { useState, useEffect } from 'react';
import { useAuth } from '../../context/AuthContext';
import { useNavigate, useLocation } from 'react-router-dom';
import './styles.css';
import { 
  LayoutDashboard, Users, Star, FileText, 
  Settings, User, Rocket, MessageCircle, 
  Trophy, LogOut, ChevronLeft, ChevronRight,
  BarChart3, Clipboard, FileBarChart
} from "lucide-react";

const Sidebar = ({ isOpen, toggleSidebar, userRole, onNavigate }) => {
  const { logout, user } = useAuth();
  const navigate = useNavigate();
  const location = useLocation();
  const [collapsed, setCollapsed] = useState(false);

  // Get current tab from URL
  const urlParams = new URLSearchParams(location.search);
  const currentTab = urlParams.get('tab') || 'dashboard';

  // Check for collapsed state in localStorage
  useEffect(() => {
    const isCollapsed = localStorage.getItem('sidebarCollapsed') === 'true';
    setCollapsed(isCollapsed);
  }, []);

  // Save collapsed state to localStorage
  useEffect(() => {
    localStorage.setItem('sidebarCollapsed', collapsed.toString());
    
    // Dispatch custom event to notify parent about collapse state change
    window.dispatchEvent(new CustomEvent('sidebarCollapseChange', { detail: collapsed }));
  }, [collapsed]);

  const getMenuItems = () => {
    if (userRole === 'faculdade') {
      return [
        { id: 'dashboard', label: 'Dashboard', icon: <LayoutDashboard size={20} />, path: '/dashboard' },
        { id: 'interns', label: 'Estagiários', icon: <Users size={20} />, path: '/dashboard' },
        { id: 'evaluations', label: 'Avaliações', icon: <Star size={20} />, path: '/dashboard' },
        { id: 'reports', label: 'Relatórios', icon: <FileText size={20} />, path: '/dashboard' },
        { id: 'settings', label: 'Configurações', icon: <Settings size={20} />, path: '/dashboard' }
      ];
    } else if (userRole === 'funcionario') {
      return [
        { id: 'dashboard', label: 'Dashboard', icon: <LayoutDashboard size={20} />, path: '/dashboard' },
        { id: 'interns', label: 'Meus Estagiários', icon: <Users size={20} />, path: '/dashboard' },
        { id: 'evaluations', label: 'Avaliações', icon: <Clipboard size={20} />, path: '/dashboard' },
        { id: 'reports', label: 'Relatórios', icon: <FileBarChart size={20} />, path: '/dashboard' },
        { id: 'settings', label: 'Configurações', icon: <Settings size={20} />, path: '/dashboard' }
      ];
    } else {
      // Default for estagiario
      return [
        { id: 'dashboard', label: 'Dashboard', icon: <LayoutDashboard size={20} />, path: '/dashboard' },
        { id: 'profile', label: 'Meu Perfil', icon: <User size={20} />, path: '/dashboard' },
        { id: 'skills', label: 'Competências', icon: <Rocket size={20} />, path: '/dashboard' },
        { id: 'feedback', label: 'Feedbacks', icon: <MessageCircle size={20} />, path: '/dashboard' },
        { id: 'achievements', label: 'Conquistas', icon: <Trophy size={20} />, path: '/dashboard' }
      ];
    }
  };

  const handleNavigation = (path, item) => {
    if (onNavigate) {
      onNavigate(path, item);
    } else {
      navigate(path);
    }
    if (window.innerWidth <= 768) {
      toggleSidebar();
    }
  };

  const toggleCollapse = () => {
    setCollapsed(!collapsed);
  };

  return (
    <>
      {isOpen && <div className="sidebar-overlay" onClick={toggleSidebar}></div>}
      <div className={`sidebar ${isOpen ? 'open' : ''} ${collapsed ? 'collapsed' : ''}`}>
        <div className="sidebar-header">
          {!collapsed && (
            <div className="logo">
              <img src="/assets/images/logoSemTexto.png" alt="Logo" />
              <span className="logo-text">SkillManager</span>
            </div>
          )}
          <button className="close-btn" onClick={toggleSidebar}>×</button>
        </div>
        
        <div className="user-profile">
          <div className="user-avatar">
            {user?.nome ? user.nome.charAt(0).toUpperCase() : 'U'}
          </div>
          {!collapsed && (
            <div className="user-info">
              <span className="user-name">{user?.nome || 'Usuário'}</span>
              <span className="user-role">
                {userRole === 'faculdade' && 'Coordenador'}
                {userRole === 'funcionario' && 'Supervisor'}
                {userRole === 'estagiario' && 'Estagiário'}
              </span>
            </div>
          )}
        </div>
        
        <nav className="sidebar-nav">
          <ul>
            {getMenuItems().map((item) => (
              <li key={item.id}>
                <button
                  className={`nav-item ${currentTab === item.id || (currentTab === 'dashboard' && item.id === 'dashboard') ? 'active' : ''}`}
                  onClick={() => handleNavigation(item.path, item.id)}
                >
                  <span className="nav-icon">{item.icon}</span>
                  {!collapsed && <span className="nav-text">{item.label}</span>}
                </button>
              </li>
            ))}
          </ul>
        </nav>
        
        <div className="sidebar-footer">
          <button className="logout-btn" onClick={logout}>
            <span className="nav-icon"><LogOut size={20} /></span>
            {!collapsed && <span className="nav-text">Sair</span>}
          </button>
          <button className="collapse-btn" onClick={toggleCollapse}>
            <span className="nav-icon">{collapsed ? <ChevronRight size={20} /> : <ChevronLeft size={20} />}</span>
            {!collapsed && <span className="nav-text">{collapsed ? 'Expandir' : 'Recolher'}</span>}
          </button>
        </div>
      </div>
    </>
  );
};

export default Sidebar;
import React, { useState } from 'react';
import { useAuth } from '../../context/AuthContext';
import { useLocation, useNavigate } from 'react-router-dom';
import HeaderDashboard from '../../components/headerDashboard';
import DashboardEstagiario from '../../components/dashboardEstagiario';
import DashboardFaculdade from '../../components/dashboardFaculdade';
import DashboardFuncionario from '../../components/dashboardFuncionario';

function TelaDashboard() {
  const { user } = useAuth();
  const location = useLocation();
  const navigate = useNavigate();

  // Get the tab from URL query parameters
  const urlParams = new URLSearchParams(location.search);
  const initialTab = urlParams.get('tab') || 'dashboard';

  // Determine which dashboard to show based on user role
  const renderDashboard = () => {
    if (user && user.roles) {
      if (user.roles.includes('ROLE_FACULDADE')) {
        return <DashboardFaculdade initialTab={initialTab} />;
      }
      if (user.roles.includes('ROLE_SUPERVISOR') || user.roles.includes('ROLE_FUNCIONARIO')) {
        return <DashboardFuncionario initialTab={initialTab} />;
      }
      // Default to estagiario dashboard for other roles or when no specific role matches
      return <DashboardEstagiario initialTab={initialTab} />;
    }
    // Fallback to estagiario dashboard if no user data
    return <DashboardEstagiario initialTab={initialTab} />;
  };

  return (
    <div className="dashboard-layout">
      <div className="main-content">
        <HeaderDashboard />
        <div className="dashboard-container">
          {renderDashboard()}
        </div>
      </div>
    </div>
  );
}

export default TelaDashboard;
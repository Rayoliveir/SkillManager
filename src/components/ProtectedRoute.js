import React from 'react';
import { Navigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

const ProtectedRoute = ({ children }) => {
    const { isAuthenticated } = useAuth();
    
    // Disable authentication during development
    const isDevelopment = process.env.NODE_ENV === 'development';
    
    // Always allow access in development mode
    if (isDevelopment) {
        return children;
    }
    
    // Production authentication check
    if (!isAuthenticated) {
        return <Navigate to="/login" />;
    }

    return children;
};

export default ProtectedRoute;
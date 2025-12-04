import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter as Router } from 'react-router-dom';
import App from './App';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    {/* Este é o ÚNICO Router em toda a aplicação */}
    <Router>
      <App />
    </Router>
  </React.StrictMode>
);
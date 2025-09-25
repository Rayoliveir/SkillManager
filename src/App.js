import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import TelaInicial from './pages/TelaInicial/index.js';
import TelaCadastro from './pages/TelaCadastro/index.js';
import TelaLogin from './pages/TelaLogin/index.js';
import TelaEquipe from './pages/TelaEquipe/index.js'
import TelaContato from './pages/TelaContato/index.js';
import TelaCadastroEstagiario from './pages/TelaCadastroEstagiario/index.js';


function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<TelaInicial />} />
        <Route path="/login" element={<TelaLogin />} />
        <Route path="/cadastro" element={<TelaCadastro />} />
        <Route path="/sobre" element={<TelaEquipe />} />
        <Route path="/contato" element={<TelaContato />} />
        <Route path="/cadastroEstagiario" element={<TelaCadastroEstagiario />} /> 
      </Routes>
    </BrowserRouter>
  );
}

export default App;
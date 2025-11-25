import { Routes, Route } from 'react-router-dom';
import { AuthProvider } from './context/AuthContext';
import ProtectedRoute from './components/ProtectedRoute';

// Importação das suas PÁGINAS (verifique se os nomes e caminhos estão corretos)
import TelaInicial from './pages/TelaInicial'; 
import TelaLogin from './pages/TelaLogin';
import TelaContato from './pages/TelaContato';
import TelaDashboard from './pages/TelaDashboard'; 
import TelaCadastro from './pages/TelaCadastro'; 
import TelaQuemSomos from './pages/TelaQuemSomos';
import TelaEsqueciSenha from './pages/TelaEsqueciSenha';

// --- NOVAS ROTAS DE CADASTRO ---
import TelaCadastroEstagiario from './pages/TelaCadastroEstagiario';
import TelaCadastroFaculdade from './pages/TelaCadastroFaculdade';
import TelaCadastroFuncionario from './pages/TelaCadastroFuncionario';

// --- NOVAS ROTAS DE DASHBOARD ---
import TelaDashboardFuncionario from './pages/TelaDashboardFuncionario';
import TelaDashboardFaculdade from './pages/TelaDashboardFaculdade';
import TelaDashboardEstagiario from './pages/TelaDashboardEstagiario';

function App() {
  return (
    <AuthProvider>
      <Routes>
        {/* --- ROTAS PÚBLICAS --- */}
        <Route path="/" element={<TelaInicial />} />
        <Route path="/quem-somos" element={<TelaQuemSomos />} />
        <Route path="/contato" element={<TelaContato />} />
        <Route path="/login" element={<TelaLogin />} />
        <Route path="/esqueci-senha" element={<TelaEsqueciSenha />} />
        
        {/* --- ROTAS DE CADASTRO --- */}
        <Route path="/cadastro" element={<TelaCadastro />} />
        <Route path="/cadastroEstagiario" element={<TelaCadastroEstagiario />} />
        <Route path="/cadastroFaculdade" element={<TelaCadastroFaculdade />} />
        <Route path="/cadastroFuncionario" element={<TelaCadastroFuncionario />} />
        
        {/* --- ROTAS PROTEGIDAS (PRIVADAS) --- */}
        <Route 
          path="/dashboard" 
          element={
            <ProtectedRoute>
              <TelaDashboard />
            </ProtectedRoute>
          } 
        />
        <Route 
          path="/dashboardFuncionario" 
          element={
            <ProtectedRoute>
              <TelaDashboardFuncionario />
            </ProtectedRoute>
          } 
        />
        <Route 
          path="/dashboardFaculdade" 
          element={
            <ProtectedRoute>
              <TelaDashboardFaculdade />
            </ProtectedRoute>
          } 
        />
        <Route 
          path="/dashboardEstagiario" 
          element={
            <ProtectedRoute>
              <TelaDashboardEstagiario />
            </ProtectedRoute>
          } 
        />
        
        {/* Rota padrão para qualquer outro caminho */}
        <Route path="*" element={<TelaInicial />} />
      </Routes>
    </AuthProvider>
  );
}

export default App;
import { Routes, Route } from 'react-router-dom';
import { AuthProvider } from './context/AuthContext';
import ProtectedRoute from './components/ProtectedRoute';

// --- MUDANÇA: Importa o novo Roteador de Dashboard ---
import DashboardRouter from './components/DashboardRouter'; // Verifique este caminho

// Importação das suas PÁGINAS
import TelaInicial from './pages/TelaInicial'; 
import TelaLogin from './pages/TelaLogin';
import TelaContato from './pages/TelaContato';
// import TelaDashboard from './pages/TelaDashboard'; // --- MUDANÇA: Não mais usado aqui
import TelaCadastro from './pages/TelaCadastro'; 
import TelaQuemSomos from './pages/TelaQuemSomos';

// --- ROTAS DE CADASTRO ---
import TelaCadastroEstagiario from './pages/TelaCadastroEstagiario';
import TelaCadastroFaculdade from './pages/TelaCadastroFaculdade';
import TelaCadastroFuncionario from './pages/TelaCadastroFuncionario';

// --- MUDANÇA: Dashboards individuais não são mais rotas diretas ---
// import TelaDashboardFuncionario from './pages/TelaDashboardFuncionario';
// import TelaDashboardFaculdade from './pages/TelaDashboardFaculdade';
// import TelaDashboardEstagiario from './pages/TelaDashboardEstagiario';

function App() {
  return (
    <AuthProvider>
      <Routes>
        {/* --- ROTAS PÚBLICAS --- */}
        <Route path="/" element={<TelaInicial />} />
        <Route path="/quem-somos" element={<TelaQuemSomos />} />
        <Route path="/contato" element={<TelaContato />} />
        <Route path="/login" element={<TelaLogin />} />
        
        {/* --- ROTAS DE CADASTRO --- */}
        <Route path="/cadastro" element={<TelaCadastro />} />
        <Route path="/cadastroEstagiario" element={<TelaCadastroEstagiario />} />
        <Route path="/cadastroFaculdade" element={<TelaCadastroFaculdade />} />
        <Route path="/cadastroFuncionario" element={<TelaCadastroFuncionario />} />
        
        {/* --- ROTA PROTEGIDA ÚNICA --- */}
        {/* --- MUDANÇA: Todas as rotas de dashboard foram unificadas --- */}
        <Route 
          path="/dashboard" 
          element={
            <ProtectedRoute>
              {/* Este novo componente decide qual dashboard mostrar */}
              <DashboardRouter />
            </ProtectedRoute>
          } 
        />
        
        {/* --- MUDANÇA: Rotas de dashboard específicas removidas --- */}
        
        {/* Rota padrão para qualquer outro caminho */}
        <Route path="*" element={<TelaInicial />} />
      </Routes>
    </AuthProvider>
  );
}

export default App;
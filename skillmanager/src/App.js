import './App.css';
import Dashboard from '../src/pages/dashboard';

function App() {
  return (
   <Router>
      <Routes>
        <Route path="/" element={<Dashboard />} />
        {/* <Route path="/cadastro" element={<QuemSomos />} /> */}
        {/* <Route path="/usuarios" element={<FaleConosco />} /> */}
        {/* <Route path="/usuarios" element={<Login />} /> */}
      </Routes>
    </Router>
  );
}

export default App;

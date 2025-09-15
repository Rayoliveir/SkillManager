import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Header from './Header';
import Home from './pages/Home';
// import Sobre from './pages/Sobre';
// import FaleConosco from './pages/FaleConosco';
// import Contato from './pages/Contato';

function App() {
  return (
    <BrowserRouter>
      <Header />
      <Routes>
        <Route path="/" element={<Home />} />
        {/* <Route path="/sobre" element={<Sobre />} /> */}
        {/* <Route path="/fale-conosco" element={<FaleConosco />} /> */}
        {/* <Route path="/contato" element={<Contato />} /> */}
      </Routes>
    </BrowserRouter>
  );
}

export default App;

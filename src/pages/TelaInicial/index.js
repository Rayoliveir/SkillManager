import './styles.css';
import Header from '../../components/header';
import Inicial from '../../components/inicial';
import Footer from '../../components/footer';

function TelaInicial() {
     return (
         <div className="pag_inicial-container">
             <Header />
             { <Inicial /> }
             <Footer />
         </div>
     );
}

export default TelaInicial
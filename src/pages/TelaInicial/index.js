import './styles.css';
import Header from '../../components/header';
import Inicial from '../../components/inicial';


function TelaInicial() {
     return (
         <div className="pag_inicial-container">
             <Header />
             { <Inicial /> }
         </div>
     );
}

export default TelaInicial
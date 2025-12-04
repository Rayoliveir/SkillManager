import './styles.css';
import Header from "../../components/header";
import Equipe from "../../components/equipe";
import Footer from "../../components/footer";

function TelaEquipe() {
    return (
        <div className="pag_equipe-container">
            <Header></Header>
            <Equipe />
            <Footer />
        </div>
    );
}

export default TelaEquipe;
import './styles.css';
import Header from '../../components/header';
import Login from '../../components/login';
import Footer from '../../components/footer';

function TelaLogin() {
    return (
        <div className="pag_login-container">
            <Header />
            <Login />
            <Footer />
        </div>
    );
}

export default TelaLogin;
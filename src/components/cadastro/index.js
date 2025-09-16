import { useNavigate } from 'react-router-dom';
import './styles.css'
// import Header from '../header';

function Cadastro() {
    const navigate = useNavigate();
    const irParaEstagiario = () => {
        navigate('/cadastroEstagiario');

        // const irParaFaculdade = () => {
        //     navigate('/');
        // const irParaFuncionario = () => {
        //     navigate('/');

    }
    return (
        <main>
            <div class="escolha-cadastro">
                <div class="titulo">
                    <h1>Quem você quer cadastrar?</h1>
                    <p>Escolha o tipo de usuário que deseja registrar no sistema</p>
                </div>

                <div class="cards">
                    <div class="card-escolha">
                        <div>
                            <img src='./assets/images/icone-estagiario.png' alt="icone-estagiario" />
                        </div>

                        <h2>Estagiário</h2>
                        <aside>Cadastro para estudantes em estágio</aside>
                        <br /> <br /><br />
                        <button onClick={irParaEstagiario}>Selecionar</button>
                    </div>
                    <div class="card-escolha">
                        <div>
                            <img src='./assets/images/icone-faculdade.png' alt="icone-estagiario" />
                        </div>

                        <h2>Faculdade</h2>
                        <aside>Registo de instituição de ensino</aside>
                        <br /> <br /><br />
                        <button onClick={irParaEstagiario}>Selecionar</button>
                    </div>
                    <div class="card-escolha">
                        <div>
                            <img src='./assets/images/icone-funcionario.png' alt="icone-estagiario" />
                        </div>

                        <h2>Supervisor</h2>
                        <aside>Cadastro de colaboradores da empresa</aside>
                        <br /> <br /><br />
                        <button onClick={irParaEstagiario}>Selecionar</button>
                    </div>
                </div>

            </div>
        </main>
    );
}

export default Cadastro;
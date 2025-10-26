import { useNavigate } from 'react-router-dom';
import './styles.css'

function Cadastro() {
    const navigate = useNavigate();

    const irParaEstagiario = () => navigate('/cadastroEstagiario');
    const irParaFaculdade = () => navigate('/cadastroFaculdade');
    const irParaFuncionario = () => navigate('/cadastroFuncionario');

    return (
        <main className="main-cadastro"> {/* <-- MUDANÇA APLICADA AQUI */}
            <div className="escolha-cadastro">
                <div className="titulo">
                    <h1>Quem você quer cadastrar?</h1>
                    <p>Escolha o tipo de usuário que deseja registrar no sistema</p>
                </div>

                <div className="cards">
                    <div className="card-escolha">
                        <div>
                            <img src='./assets/images/icone-estagiario.png' alt="icone-estagiario" />
                        </div>
                        <h2>Estagiário</h2>
                        <aside>Cadastro para estudantes em estágio</aside>
                        <br /> <br /><br />
                        <button onClick={irParaEstagiario}>Selecionar</button>
                    </div>
                    <div className="card-escolha">
                        <div>
                            <img src='./assets/images/icone-faculdade.png' alt="icone-faculdade" />
                        </div>
                        <h2>Faculdade</h2>
                        <aside>Cadastro de instituição de ensino</aside>
                        <br /> <br /><br />
                        <button onClick={irParaFaculdade}>Selecionar</button>
                    </div>
                    <div className="card-escolha">
                        <div>
                            <img src='./assets/images/icone-funcionario.png' alt="icone-supervisor" />
                        </div>
                        <h2>Supervisor</h2>
                        <aside>Cadastro de colaboradores da empresa</aside>
                        <br /> <br /><br />
                        <button onClick={irParaFuncionario}>Selecionar</button>
                    </div>
                </div>
            </div>
        </main>
    );
}

export default Cadastro;
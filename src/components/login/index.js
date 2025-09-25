import './styles.css';
import { Link, useNavigate } from 'react-router-dom';
import { useState } from 'react';

import useMensagem from '../../hooks/useMensagem';
import MensagemFeedback from '../mensagemFeedback';

function Login() {
    const [login, setLogin] = useState('');
    const [senha, setSenha] = useState('');
    const navigate = useNavigate();
    const { mensagem, tipoMensagem, visivel, exibirMensagem, fecharMensagem } = useMensagem();

    async function handleLogin(e) {
        e.preventDefault();

        // Adicionando a validação de campos vazios
        if (!login || !senha) {
            exibirMensagem('Preencha todos os campos para continuar.', 'erro');
            return; // Interrompe a execução da função
        }
        
        try {
            // SIMULAÇÃO DA RESPOSTA DO BACK-END
            const resultadoSimulado = new Promise((resolve, reject) => {
                setTimeout(() => {
                    // Simule um erro para testar a mensagem de erro
                    // descomente a linha abaixo para testar o erro
                    // reject({ response: { status: 401 } });

                    // Simule um sucesso
                    resolve({ data: { token: 'seu-token-de-exemplo' } });
                }, 1000);
            });

            const response = await resultadoSimulado;

            localStorage.setItem('token', response.data.token);
            localStorage.setItem('login', login);

            exibirMensagem('Login realizado com sucesso!', 'sucesso');
            
            setTimeout(() => {
                navigate('/dashboard');
            }, 1000);

        } catch (error) {
            if (error.response && error.response.status === 401) {
                exibirMensagem('Login ou senha incorretos. Tente novamente.', 'erro');
            } else {
                exibirMensagem('Ocorreu um erro ao tentar conectar com o servidor.', 'erro');
            }
        }
    }

    return (
        <main className="main-login">
            <MensagemFeedback
                mensagem={mensagem}
                tipo={tipoMensagem}
                visivel={visivel}
                onClose={fecharMensagem}
            />
            <div className="gilson">
                <div className="logo-skill">
                    <img src="/assets/images/logoSemTexto.png" alt="logoSemTexto" />
                    <h2>Faça login na sua conta</h2>
                </div>


                <form onSubmit={handleLogin} className="form-login">
                    <label>E-mail: <br />
                        <input
                            type="email"
                            name="email"
                            id="email"
                            placeholder="Digite seu e-mail"
                            value={login}
                            onChange={(e) => setLogin(e.target.value)}
                        />
                    </label>

                    <label>Senha: <br />
                        <input
                            type="password"
                            name="senha"
                            id="senha"
                            placeholder="Digite sua senha"
                            value={senha}
                            onChange={(e) => setSenha(e.target.value)}
                        />
                    </label>

                    <div>
                        <button type="submit">Entrar</button>
                    </div>
                    <p>Não tem uma conta? Crie sua conta <Link to="/cadastro">aqui</Link></p>
                </form>
            </div>
        </main>
    );
}

export default Login;
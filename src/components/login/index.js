import './styles.css'
import { Link } from 'react-router-dom';


function Login() {
    return (
        <main class="main-login">
            <div class="gilson">
                <div class="logo-skill">
                    <img src="/assets/images/logoSemTexto.png" alt="logoSemTexto" />
                    <h2>Faça login na sua conta</h2>
                </div>


                <form action="" class="form-login">
                    <label for="">E-mail: <br />
                        <input type="email" name="email" id="email" placeholder="Digite seu e-mail" />
                    </label>

                    <label for="">Senha: <br />
                        <input type="password" name="senha" id="senha" placeholder="Digite sua senha" />
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
import './styles.css';
import Header from '../../components/header';
import Footer from '../../components/footer';

function TelaContato() {
    return (
        <div className='pag_contato-container'>
            <Header></Header>
            <main className="contato-content">
                <section className="contato-header">
                    <h1>Fale Conosco</h1>
                    <p className="subtitle">Entre em contato conosco para tirar dúvidas ou enviar sugestões</p>
                </section>
                
                <section className="contato-main">
                    <div className="contact-info">
                        <h2>Informações de Contato</h2>
                        <div className="info-item">
                            <h3>Email</h3>
                            <p>contato@skillmanager.com</p>
                        </div>
                        <div className="info-item">
                            <h3>Telefone</h3>
                            <p>(71) 99999-9999</p>
                        </div>
                        <div className="info-item">
                            <h3>Endereço</h3>
                            <p>Rua Exemplo, 123 - Salvador, BA</p>
                        </div>
                    </div>
                    
                    <div className="contact-form">
                        <h2>Envie uma mensagem</h2>
                        <form>
                            <div className="form-group">
                                <label htmlFor="name">Nome</label>
                                <input type="text" id="name" placeholder="Seu nome" />
                            </div>
                            <div className="form-group">
                                <label htmlFor="email">Email</label>
                                <input type="email" id="email" placeholder="seu.email@exemplo.com" />
                            </div>
                            <div className="form-group">
                                <label htmlFor="subject">Assunto</label>
                                <input type="text" id="subject" placeholder="Assunto da mensagem" />
                            </div>
                            <div className="form-group">
                                <label htmlFor="message">Mensagem</label>
                                <textarea id="message" rows="5" placeholder="Sua mensagem"></textarea>
                            </div>
                            <button type="submit" className="submit-btn">Enviar Mensagem</button>
                        </form>
                    </div>
                </section>
            </main>
            <Footer />
        </div>
    );
}

export default TelaContato;
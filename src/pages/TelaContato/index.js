import './styles.css';
import Header from '../../components/header';
import Footer from '../../components/footer';
import { useState } from 'react';
import * as api from '../../services/api';

function TelaContato() {
    const [formData, setFormData] = useState({
        name: '',
        email: '',
        subject: '',
        message: ''
    });
    const [isSubmitting, setIsSubmitting] = useState(false);
    const [submitStatus, setSubmitStatus] = useState(null);

    const handleChange = (e) => {
        const { id, value } = e.target;
        setFormData(prev => ({
            ...prev,
            [id]: value
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setIsSubmitting(true);
        setSubmitStatus(null);

        try {
            // Prepare email data
            const emailData = {
                to: 'skillmanagercontato@gmail.com',
                from: formData.email,
                fromName: formData.name,
                subject: `[Contato Site] ${formData.subject}`,
                message: formData.message
            };

            // Send email using API
            const response = await api.enviarEmailContato(emailData);

            // Show success message
            setSubmitStatus('success');
            setFormData({ name: '', email: '', subject: '', message: '' });
        } catch (error) {
            console.error('Error sending email:', error);
            setSubmitStatus('error');
        } finally {
            setIsSubmitting(false);
        }
    };

    return (
        <div className='pag_contato-container'>
            <Header></Header>
            <main className="contato-content">
                <section className="contato-header">
                    <h1>Fale conosco</h1>
                    <p className="subtitle">Entre em contato conosco para tirar dúvidas ou enviar sugestões</p>
                </section>
                
                <section className="contato-main">
                    <div className="contact-info">
                        <h2>Informações de contato</h2>
                        <div className="info-item">
                            <h3>Email</h3>
                            <p>skillmanagercontato@gmail.com</p>
                        </div>
                        {/* <div className="info-item">
                            <h3>Telefone</h3>
                            <p>(71) 99999-9999</p>
                        </div> */}
                        <div className="info-item">
                            <h3>Endereço</h3>
                            <p>Salvador, BA</p>
                        </div>
                    </div>
                    
                    <div className="contact-form">
                        <h2>Envie uma mensagem</h2>
                        {submitStatus === 'success' && (
                            <div className="success-message">
                                Mensagem enviada com sucesso! Entraremos em contato em breve.
                            </div>
                        )}
                        {submitStatus === 'error' && (
                            <div className="error-message">
                                Ocorreu um erro ao enviar a mensagem. Por favor, tente novamente.
                            </div>
                        )}
                        <form onSubmit={handleSubmit}>
                            <div className="form-group">
                                <label htmlFor="name">Nome</label>
                                <input 
                                    type="text" 
                                    id="name" 
                                    placeholder="Seu nome" 
                                    value={formData.name}
                                    onChange={handleChange}
                                    required
                                />
                            </div>
                            <div className="form-group">
                                <label htmlFor="email">Email</label>
                                <input 
                                    type="email" 
                                    id="email" 
                                    placeholder="seu.email@exemplo.com" 
                                    value={formData.email}
                                    onChange={handleChange}
                                    required
                                />
                            </div>
                            <div className="form-group">
                                <label htmlFor="subject">Assunto</label>
                                <input 
                                    type="text" 
                                    id="subject" 
                                    placeholder="Assunto da mensagem" 
                                    value={formData.subject}
                                    onChange={handleChange}
                                    required
                                />
                            </div>
                            <div className="form-group">
                                <label htmlFor="message">Mensagem</label>
                                <textarea 
                                    id="message" 
                                    rows="5" 
                                    placeholder="Sua mensagem" 
                                    value={formData.message}
                                    onChange={handleChange}
                                    required
                                ></textarea>
                            </div>
                            <button 
                                type="submit" 
                                className="submit-btn" 
                                disabled={isSubmitting}
                            >
                                {isSubmitting ? 'Enviando...' : 'Enviar mensagem'}
                            </button>
                        </form>
                    </div>
                </section>
            </main>
            <Footer />
        </div>
    );
}

export default TelaContato;
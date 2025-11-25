import React, { useState, useEffect } from 'react';
import './styles.css';

// Componente de Input de Nota (1 a 5)
const NotaInput = ({ label, value, name, onChange }) => {
    const [hoverRating, setHoverRating] = useState(0);

    const handleRatingClick = (rating) => {
        onChange(name, rating);
    };

    const renderStars = () => {
        return Array(5).fill(0).map((_, i) => {
            const starValue = i + 1;
            const isFilled = starValue <= (hoverRating || value);
            
            return (
                <span 
                    key={i}
                    className={`star ${isFilled ? 'filled' : ''}`}
                    onClick={() => handleRatingClick(starValue)}
                    onMouseEnter={() => setHoverRating(starValue)}
                    onMouseLeave={() => setHoverRating(0)}
                >
                    ★
                </span>
            );
        });
    };

    return (
        <div className="form-group nota-group">
            <label>{label}:</label>
            <div className="rating-input">
                {renderStars()}
                <span className="rating-text" style={{marginLeft: '10px', fontSize: '0.9rem'}}>
                    {value}/5
                </span>
            </div>
        </div>
    );
};

const FormularioAvaliacao = ({ estagiario, onSubmit, onCancel, submetendo }) => {
    // Inicializa o estado com as três notas e o feedback
    const [formData, setFormData] = useState({
        notaDesempenho: 3,
        notaHabilidadesTecnicas: 3,
        notaHabilidadesComportamentais: 3,
        feedback: ''
    });

    // Se o estagiario for null/undefined por algum motivo, renderiza nulo ou loader
    // Isso evita o erro "Cannot read properties of undefined"
    if (!estagiario) return null;

    const handleNotaChange = (name, value) => {
        const safeValue = Math.min(Math.max(1, value), 5);
        setFormData(prev => ({
            ...prev,
            [name]: safeValue
        }));
    };
    
    const handleFeedbackChange = (e) => {
         setFormData(prev => ({
            ...prev,
            feedback: e.target.value
        }));
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        
        if (formData.feedback.trim().length < 10) {
            alert('O feedback é obrigatório e deve ter pelo menos 10 caracteres.');
            return;
        }
        
        // Envia os dados de volta para o Dashboard
        onSubmit({
            ...formData,
            estagiarioId: estagiario.id
        });
    };
    
    const isFormValid = formData.feedback.trim().length >= 10 && 
                        formData.notaDesempenho > 0 && 
                        formData.notaHabilidadesTecnicas > 0 && 
                        formData.notaHabilidadesComportamentais > 0;

    return (
        <div className="evaluation-form-overlay">
            <div className="evaluation-form-modal">
                <div className="form-header">
                    <h3>Avaliar estagiário</h3>
                    {/* CORREÇÃO DO ERRO: Usamos .nome (do backend) e verificamos se estagiario existe */}
                    <h4>{estagiario.nome || 'Nome não disponível'}</h4>
                </div>
                
                <form onSubmit={handleSubmit}>
                    <div className="secao-notas" style={{marginBottom: '20px'}}>
                        <h4 style={{marginBottom: '15px', color: '#2d3748'}}>Critérios de avaliação</h4>
                        
                        <NotaInput 
                            label="Desempenho geral" 
                            name="notaDesempenho"
                            value={formData.notaDesempenho}
                            onChange={handleNotaChange}
                        />
                         <NotaInput 
                            label="Habilidades técnicas" 
                            name="notaHabilidadesTecnicas"
                            value={formData.notaHabilidadesTecnicas}
                            onChange={handleNotaChange}
                        />
                         <NotaInput 
                            label="Habilidades comportamentais" 
                            name="notaHabilidadesComportamentais"
                            value={formData.notaHabilidadesComportamentais}
                            onChange={handleNotaChange}
                        />
                    </div>

                    <div className="form-group">
                        <label htmlFor="feedback">Feedback detalhado (Mínimo 10 caracteres):</label>
                        <textarea 
                            id="feedback"
                            name="feedback"
                            value={formData.feedback}
                            onChange={handleFeedbackChange}
                            placeholder="Descreva o desempenho do estagiário, pontos fortes e áreas de melhoria..."
                            className="form-textarea" // Usa a classe do seu CSS existente
                            rows="5"
                            required
                        />
                    </div>
                    
                    <div className="form-actions">
                        <button 
                            type="button" 
                            className="btn-secondary" // Classe do seu CSS existente
                            onClick={onCancel}
                            disabled={submetendo}
                        >
                            Cancelar
                        </button>
                        <button 
                            type="submit" 
                            className="btn-primary" // Classe do seu CSS existente
                            disabled={!isFormValid || submetendo}
                        >
                            {submetendo ? 'Registrando...' : 'Registrar Avaliação'}
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
};

export default FormularioAvaliacao;
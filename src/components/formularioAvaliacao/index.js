import React, { useState } from 'react';
import './styles.css';

const FormularioAvaliacao = ({ intern, onSubmit, onCancel }) => {
    const [formData, setFormData] = useState({
        descricao: '',
        classificacao: 0
    });
    const [hoverRating, setHoverRating] = useState(0);

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setFormData(prev => ({
            ...prev,
            [name]: value
        }));
    };

    const handleRatingClick = (rating) => {
        setFormData(prev => ({
            ...prev,
            classificacao: rating
        }));
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        if (formData.descricao.trim() && formData.classificacao > 0) {
            onSubmit({
                ...formData,
                estagiarioId: intern.id,
                estagiarioNome: intern.name,
                data: new Date().toLocaleDateString('pt-BR')
            });
        }
    };

    const renderStars = (rating, interactive = false) => {
        return Array(5).fill(0).map((_, i) => {
            const starValue = i + 1;
            let className = 'star';
            
            if (interactive) {
                if (starValue <= (hoverRating || formData.classificacao)) {
                    className += ' filled';
                }
            } else {
                if (starValue <= rating) {
                    className += ' filled';
                }
            }
            
            return (
                <span 
                    key={i}
                    className={className}
                    onClick={interactive ? () => handleRatingClick(starValue) : undefined}
                    onMouseEnter={interactive ? () => setHoverRating(starValue) : undefined}
                    onMouseLeave={interactive ? () => setHoverRating(0) : undefined}
                >
                    ★
                </span>
            );
        });
    };

    return (
        <div className="evaluation-form-overlay">
            <div className="evaluation-form-modal">
                <div className="form-header">
                    <h3>Avaliar Estagiário</h3>
                    <h4>{intern.name}</h4>
                </div>
                
                <form onSubmit={handleSubmit}>
                    <div className="form-group">
                        <label htmlFor="descricao">Descrição da Avaliação:</label>
                        <textarea 
                            id="descricao"
                            name="descricao"
                            value={formData.descricao}
                            onChange={handleInputChange}
                            placeholder="Descreva o desempenho do estagiário..."
                            className="form-textarea"
                            required
                        />
                    </div>
                    
                    <div className="form-group">
                        <label>Classificação:</label>
                        <div className="rating-input">
                            {renderStars(formData.classificacao, true)}
                        </div>
                        {formData.classificacao > 0 && (
                            <span className="rating-text">
                                {formData.classificacao === 1 && 'Ruim'}
                                {formData.classificacao === 2 && 'Regular'}
                                {formData.classificacao === 3 && 'Bom'}
                                {formData.classificacao === 4 && 'Muito Bom'}
                                {formData.classificacao === 5 && 'Excelente'}
                            </span>
                        )}
                    </div>
                    
                    <div className="form-actions">
                        <button 
                            type="button" 
                            className="btn-secondary"
                            onClick={onCancel}
                        >
                            Cancelar
                        </button>
                        <button 
                            type="submit" 
                            className="btn-primary"
                            disabled={!formData.descricao.trim() || formData.classificacao === 0}
                        >
                            Registrar Avaliação
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
};

export default FormularioAvaliacao;
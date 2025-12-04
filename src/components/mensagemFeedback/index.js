// src\components\MensagemFeedback\index.js

import './styles.css'

function MensagemFeedback({ mensagem, tipo, visivel, onClose }) {
    if (!visivel) {
        return null
    }

    return (
        <div 
            id='mensagem' 
            className={`mensagem ${tipo} visivel`}
            onClick={onClose}
        >
            {mensagem}
        </div>
    )
}

export default MensagemFeedback
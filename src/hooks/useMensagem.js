// src\hooks\useMensagem.js

import { useState, useCallback } from 'react';

function useMensagem() {
  const [mensagem, setMensagem] = useState('');
  const [tipoMensagem, setTipoMensagem] = useState('');
  const [visivel, setVisivel] = useState('');

  const exibirMensagem = useCallback((mensagem, tipo = 'sucesso') => {
    setMensagem(mensagem);
    setTipoMensagem(tipo);
    setVisivel(true);

    // Ocultar a mensagem após 3 segundos
    setTimeout(() => setVisivel(false), 3000)
    }, []);

    const fecharMensagem = useCallback(() => {
      setVisivel(false);
    }, []);

    return {
      mensagem,
      tipoMensagem,
      visivel,
      exibirMensagem,
      fecharMensagem,
    };

}

export default useMensagem;
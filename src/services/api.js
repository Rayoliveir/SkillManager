const API_URL = 'http://localhost:8080';

const handleResponse = async (response) => {
    if (!response.ok) {
        const errorText = await response.text();
        console.error("Resposta de erro do servidor:", errorText);
        throw new Error(`Erro ${response.status}: ${response.statusText}`);
    }
    const text = await response.text();
    return text ? JSON.parse(text) : {};
};


export const login = async (login, senha) => {

    const token = btoa(`${login}:${senha}`);

    const response = await fetch(`${API_URL}/login`, {
        method: 'POST',
        headers: {
            'Authorization': `Basic ${token}`,
            'Content-Type': 'application/json',
        },
    });

    const data = await handleResponse(response);

    if (response.ok) {
        localStorage.setItem('authToken', token);
    }
    
    return data;
};

export const cadastrarEstagiario = async (estagiarioData) => {
    const response = await fetch(`${API_URL}/estagiarios`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(estagiarioData),
    });
    return handleResponse(response);
};

export const cadastrarFuncionario = async (funcionarioData) => {
    const response = await fetch(`${API_URL}/funcionarios`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(funcionarioData),
    });
    return handleResponse(response);
};

export const cadastrarFaculdade = async (faculdadeData) => {
    const response = await fetch(`${API_URL}/faculdades`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(faculdadeData),
    });
    return handleResponse(response);
};

export const getSupervisorDashboard = async () => {
    const token = localStorage.getItem('authToken');
    const response = await fetch(`${API_URL}/dashboard/supervisor`, {
        method: 'GET',
        headers: {
            'Authorization': `Basic ${token}`,
            'Content-Type': 'application/json',
        },
    });
    return handleResponse(response);
};

export const getFaculdadeDashboard = async () => {
    const token = localStorage.getItem('authToken');
    const response = await fetch(`${API_URL}/dashboard/faculdade`, {
        method: 'GET',
        headers: {
            'Authorization': `Basic ${token}`,
            'Content-Type': 'application/json',
        },
    });
    return handleResponse(response);
};

export const getEstagiarioDashboard = async () => {
    const token = localStorage.getItem('authToken');
    const response = await fetch(`${API_URL}/dashboard/estagiario`, {
        method: 'GET',
        headers: {
            'Authorization': `Basic ${token}`,
            'Content-Type': 'application/json',
        },
    });
    return handleResponse(response);
};
import { authHeader } from '../_helpers';
import { jwtConstants, serverConstants } from '../_constants';

export const mbrService = {
    create
};

function create(name, mortgageValue, houseId) {

    const requestOptions = {
        method: 'POST',
        headers: authHeader(jwtConstants.MBR_TOKEN) + { 'Content-Type': 'application/json' },
        body: JSON.stringify({ name, mortgageValue, houseId })
    };

    return fetch(serverConstants.MBR_SERVER_CREATE_ENDPOINT, requestOptions)
    .then(response => {
        if (!response.ok) { 
            return Promise.reject(response.statusText);
        }
        //TODO: Remove this, its for testing
        console.log(response.json());
        return response.json();
    })
}

function handleResponse(response) {
    if (!response.ok) { 
        return Promise.reject(response.statusText);
    }

    return response.json();
}
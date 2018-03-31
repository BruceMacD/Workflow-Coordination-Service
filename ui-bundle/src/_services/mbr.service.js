import { authHeader } from '../_helpers';
import { jwtConstants, serverConstants } from '../_constants';

export const mbrService = {
    create,
    createUser,
    getStatus
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
            return response.json();
        }).then(responseBody => {
            return responseBody;
        });
}

function createUser(id, password) {

    const requestOptions = {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ id, password })
    };

    return fetch(serverConstants.MBR_SERVER_CREATE_USER_ENDPOINT, requestOptions)
        .then(response => {
            if (!response.ok) {
                return Promise.reject(response.statusText);
            }
            return response.json();
        }).then(responseBody => {
            return responseBody;
        });
}

function getStatus(id) {

    const requestOptions = {
        method: 'GET',
        headers: authHeader()
    };

    const endpoint = serverConstants.MBR_SERVER_GET_STATUS_ENDPOINT + '/' + id;

    return fetch( endpoint, requestOptions)
        .then(response => {
            if (!response.ok) {
                return Promise.reject(response.statusText);
            }
            return response.json();
        }).then(responseBody => {
            return responseBody;
        });
}
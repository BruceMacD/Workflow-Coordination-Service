import { authHeader } from '../_helpers';
import { jwtConstants, serverConstants, portalConstants } from '../_constants';

export const userService = {
    login,
    logout
};

function sendFetch(endpoint, storage, requestOptions) {
    return fetch(endpoint, requestOptions)
        .then(response => {
            if (!response.ok) { 
                return Promise.reject(response.statusText);
            }
            return response.json();
        })
        .then(user => {
            // login successful if there's a jwt token in the response
            if (user && user.token) {
                // store user details and jwt token in local storage to keep user logged in between page refreshes
                localStorage.setItem(storage, JSON.stringify(user));
            }

            return user;
        });
}

function login(portal, id, password) {
    const requestOptions = {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ id, password })
    };

    // allows for reusable logic in multiple portals
    // different portals save different jwt
    // TODO: handle other login portals
    switch(portal) {
        case portalConstants.MBR_PORTAL:
            return sendFetch(serverConstants.MBR_SERVER_LOGIN_ENDPOINT, jwtConstants.MBR_TOKEN, requestOptions);
    }
}

function logout(portal) {
    // remove user from local storage to log user out
    switch(portal) {
        case portalConstants.MBR_PORTAL:
            localStorage.removeItem(jwtConstants.MBR_TOKEN);
    }
}

function handleResponse(response) {
    if (!response.ok) { 
        return Promise.reject(response.statusText);
    }

    return response.json();
}
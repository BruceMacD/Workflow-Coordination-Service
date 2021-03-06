import React from 'react';
import { Route, Redirect } from 'react-router-dom';

// Redirects that check for token depending on portal

// Default redirect for reference, not used
export const PrivateRoute = ({ component: Component, ...rest }) => (
    <Route {...rest} render={props => (
        localStorage.getItem('user')
            ? <Component {...props} />
            : <Redirect to={{ pathname: '/login', state: { from: props.location } }} />
    )} />
)

export const PrivateRouteMBR = ({ component: Component, ...rest }) => (
    <Route {...rest} render={props => (
        localStorage.getItem('mbr_user')
            ? <Component {...props} />
            : <Redirect to={{ pathname: '/mbr/login', state: { from: props.location } }} />
    )} />
)

export const PrivateRouteEMP = ({ component: Component, ...rest }) => (
    <Route {...rest} render={props => (
        localStorage.getItem('emp_user')
            ? <Component {...props} />
            : <Redirect to={{ pathname: '/emp/login', state: { from: props.location } }} />
    )} />
)

export const PrivateRouteRE = ({ component: Component, ...rest }) => (
    <Route {...rest} render={props => (
        localStorage.getItem('re_user')
            ? <Component {...props} />
            : <Redirect to={{ pathname: '/re/login', state: { from: props.location } }} />
    )} />
)
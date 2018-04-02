import React from 'react';
import { Router, Route } from 'react-router-dom';
import { connect } from 'react-redux';

import { history } from '../_helpers';
import { alertActions } from '../_actions';
import { PrivateRouteMBR, PrivateRouteEMP, PrivateRouteRE } from '../_components';
import { MBRHomePage, EMPHomePage, REHomePage } from '../HomePage';
import { MBRLoginPage, EMPLoginPage, RELoginPage } from '../LoginPage';

class App extends React.Component {
    constructor(props) {
        super(props);

        const { dispatch } = this.props;
        history.listen((location, action) => {
            // clear alert on location change
            dispatch(alertActions.clear());
        });
    }

    render() {
        const { alert } = this.props;
        return (
            <div className="container">
                <div className="col-sm-8 col-sm-offset-2">
                    {alert.message &&
                        <div className={`alert ${alert.type}`}>{alert.message}</div>
                    }
                    <Router history={history}>
                        <div>
                            <PrivateRouteMBR exact path="/mbr" component={MBRHomePage} />
                            <Route path="/mbr/login" component={MBRLoginPage} />

                            <PrivateRouteEMP exact path="/emp" component={EMPHomePage} />
                            <Route path="/emp/login" component={EMPLoginPage} />

                            <PrivateRouteRE exact path="/re" component={REHomePage} />
                            <Route path="/re/login" component={RELoginPage} />
                        </div>
                    </Router>
                </div>
            </div>
        );
    }
}

function mapStateToProps(state) {
    const { alert } = state;
    return {
        alert
    };
}

const connectedApp = connect(mapStateToProps)(App);
export { connectedApp as App }; 
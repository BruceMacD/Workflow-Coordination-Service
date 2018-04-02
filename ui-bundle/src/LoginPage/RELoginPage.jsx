import React from 'react';
import { Link } from 'react-router-dom';
import { connect } from 'react-redux';
import { Alert, Modal, Button } from 'react-bootstrap';

import { portalConstants } from '../_constants';
import { userActions, reActions } from '../_actions';

class RELoginPage extends React.Component {

    constructor(props) {
        super(props);

        // reset login status
        this.props.dispatch(userActions.logout(portalConstants.RE_PORTAL));

        this.state = {
            show: false,
            id: '',
            password: '',
            submitted: false,
            creating: false,
            created: false,
            username: '',
            newPassword: ''
        };

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleShow = this.handleShow.bind(this);
        this.handleClose = this.handleClose.bind(this);
        this.handleCreateUser = this.handleCreateUser.bind(this);
    }

    handleClose() {
        this.setState({ show: false });
    }

    handleShow() {
        this.setState({ show: true });
    }

    handleChange(e) {
        const { name, value } = e.target;
        this.setState({ [name]: value });
    }

    handleSubmit(e) {
        e.preventDefault();

        this.setState({ submitted: true });
        const { id, password } = this.state;
        const { dispatch } = this.props;
        if (id && password) {
            dispatch(userActions.login(portalConstants.RE_PORTAL, id, password));
        }
    }

    handleCreateUser(e) {
        e.preventDefault();

        this.setState({ creating: true });
        const { username, newPassword } = this.state;
        const { dispatch } = this.props;
        if (username && newPassword) {
            dispatch(reActions.createUser(username, newPassword));
            this.setState({ created: true });
        }
    }

    render() {
        const visibilityState = this.state.created ? "visible" : "hidden";
        const { loggingIn } = this.props;
        const { newId } = this.props;
        const { id, password, submitted, show, creating, created, username, newPassword } = this.state;
        return (
            <div className="col-md-6 col-md-offset-3">
                <h2>Login</h2>
                <form name="form" onSubmit={this.handleSubmit}>
                    <div className={'form-group' + (submitted && !id ? ' has-error' : '')}>
                        <label htmlFor="id">User ID</label>
                        <input type="text" className="form-control" name="id" value={id} onChange={this.handleChange} />
                        {submitted && !id &&
                            <div className="help-block">User ID is required</div>
                        }
                    </div>
                    <div className={'form-group' + (submitted && !password ? ' has-error' : '')}>
                        <label htmlFor="password">Password</label>
                        <input type="password" className="form-control" name="password" value={password} onChange={this.handleChange} />
                        {submitted && !password &&
                            <div className="help-block">Password is required</div>
                        }
                    </div>
                    <div className="form-group">
                        <button className="btn btn-primary">Login</button>
                        {loggingIn &&
                            <img src="data:image/gif;base64,R0lGODlhEAAQAPIAAP///wAAAMLCwkJCQgAAAGJiYoKCgpKSkiH/C05FVFNDQVBFMi4wAwEAAAAh/hpDcmVhdGVkIHdpdGggYWpheGxvYWQuaW5mbwAh+QQJCgAAACwAAAAAEAAQAAADMwi63P4wyklrE2MIOggZnAdOmGYJRbExwroUmcG2LmDEwnHQLVsYOd2mBzkYDAdKa+dIAAAh+QQJCgAAACwAAAAAEAAQAAADNAi63P5OjCEgG4QMu7DmikRxQlFUYDEZIGBMRVsaqHwctXXf7WEYB4Ag1xjihkMZsiUkKhIAIfkECQoAAAAsAAAAABAAEAAAAzYIujIjK8pByJDMlFYvBoVjHA70GU7xSUJhmKtwHPAKzLO9HMaoKwJZ7Rf8AYPDDzKpZBqfvwQAIfkECQoAAAAsAAAAABAAEAAAAzMIumIlK8oyhpHsnFZfhYumCYUhDAQxRIdhHBGqRoKw0R8DYlJd8z0fMDgsGo/IpHI5TAAAIfkECQoAAAAsAAAAABAAEAAAAzIIunInK0rnZBTwGPNMgQwmdsNgXGJUlIWEuR5oWUIpz8pAEAMe6TwfwyYsGo/IpFKSAAAh+QQJCgAAACwAAAAAEAAQAAADMwi6IMKQORfjdOe82p4wGccc4CEuQradylesojEMBgsUc2G7sDX3lQGBMLAJibufbSlKAAAh+QQJCgAAACwAAAAAEAAQAAADMgi63P7wCRHZnFVdmgHu2nFwlWCI3WGc3TSWhUFGxTAUkGCbtgENBMJAEJsxgMLWzpEAACH5BAkKAAAALAAAAAAQABAAAAMyCLrc/jDKSatlQtScKdceCAjDII7HcQ4EMTCpyrCuUBjCYRgHVtqlAiB1YhiCnlsRkAAAOwAAAAAAAAAAAA==" />
                        }
                    </div>
                </form>

                {/* this is the create user modal */}
                <div>
                    <button className="btn btn-primary" onClick={this.handleShow}>
                        Create User
                        </button>
                    <Modal show={this.state.show} onHide={this.handleClose}>
                        <Modal.Header closeButton>
                            <Modal.Title>Create New User</Modal.Title>
                        </Modal.Header>
                        <Modal.Body>
                            <form name="form" onSubmit={this.handleCreateUser}>
                                <div className={'form-group' + (creating && !username ? ' has-error' : '')}>
                                    <label htmlFor="username">Username</label>
                                    <input type="text" className="form-control" name="username" value={username} onChange={this.handleChange} />
                                    {creating && !username &&
                                        <div className="help-block">Username is required</div>
                                    }
                                </div>
                                <div className={'form-group' + (creating && !newPassword ? ' has-error' : '')}>
                                    <label htmlFor="newPassword">Password</label>
                                    <input type="password" className="form-control" name="newPassword" value={newPassword} onChange={this.handleChange} />
                                    {creating && !newPassword &&
                                        <div className="help-block">Password is required</div>
                                    }
                                </div>
                                <div className="form-group">
                                    <button className="btn btn-primary">Create User</button>
                                </div>
                            </form>
                        </Modal.Body>
                        <Modal.Footer>
                            <Alert style={{visibility: visibilityState}} bsStyle="success">
                                <strong>Account created:</strong> Your User ID is: {newId}
                            </Alert>
                            <Button onClick={this.handleClose}>Close</Button>
                        </Modal.Footer>
                    </Modal>
                </div>
            </div>
        );
    }
}

function mapStateToProps(state) {
    const { loggingIn } = state.reAuthentication;
    const newId = state.users.id;
    return {
        loggingIn,
        newId
    };
}

const connectedRELoginPage = connect(mapStateToProps)(RELoginPage);
export { connectedRELoginPage as RELoginPage }; 
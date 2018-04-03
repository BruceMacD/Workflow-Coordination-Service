import React from 'react';
import { Link } from 'react-router-dom';
import { connect } from 'react-redux';
import { FormGroup, ControlLabel, FormControl, HelpBlock, Tabs, Tab, Alert } from 'react-bootstrap';

import { userActions, reActions } from '../_actions';

class REHomePage extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            name: '',
            mIsId: '',
            mortId: '',
            submittedForm: false,
            dispatchedForm: false
        };

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmitForm = this.handleSubmitForm.bind(this);
    }

    handleChange(e) {
        const { name, value } = e.target;
        this.setState({ [name]: value });
    }

    handleSubmitForm(e) {
        e.preventDefault();

        const { userId } = this.props;

        this.setState({ submittedForm: true });

        const { name, mIsId, mortId } = this.state;
        const { dispatch } = this.props;

        if (name && mIsId && mortId) {
            this.setState({ dispatchedForm: true });
            dispatch(reActions.submit(userId, name, mIsId, mortId))
        }
    }

    render() {
        const ackVisibilityState = this.state.dispatchedForm ? "visible" : "hidden";

        const { user } = this.props;
        const { name, mIsId, mortId, submittedForm } = this.state;

        return (
            <div>
                <h1>Real Estate</h1>
                <p>
                    <Link to="/re/login">Logout</Link>
                </p>
                <Tabs defaultActiveKey={1} id="re-tabs">
                    <Tab eventKey={1} title="Welcome">
                        <p></p>
                        <h3>Welcome to the RE Appraisals</h3>
                        <p>Request property valuations</p>
                    </Tab>
                    <Tab eventKey={2} title="Property Appraisal">
                        <p></p>
                        <form name="form" onSubmit={this.handleSubmitForm}>
                            <div className={'form-group' + (submittedForm && !name ? ' has-error' : '')}>
                                <label htmlFor="name">Name</label>
                                <input type="text" className="form-control" name="name" value={name} onChange={this.handleChange} />
                                {submittedForm && !name &&
                                    <div className="help-block">Name is required</div>
                                }
                            </div>
                            <div className={'form-group' + (submittedForm && !mIsId ? ' has-error' : '')}>
                                <label htmlFor="mIsId">Mortgage Insurance ID</label>
                                <input type="text" className="form-control" name="mIsId" value={mIsId} onChange={this.handleChange} />
                                {submittedForm && !mIsId &&
                                    <div className="help-block">Mortgage Insurance ID is required</div>
                                }
                            </div>
                            <div className={'form-group' + (submittedForm && !mortId ? ' has-error' : '')}>
                                <label htmlFor="mortId">Mortgage ID</label>
                                <input type="text" className="form-control" name="mortId" value={mortId} onChange={this.handleChange} />
                                {submittedForm && !mortId &&
                                    <div className="help-block">Mortgage ID is required</div>
                                }
                            </div>
                            <div className="form-group">
                                <button className="btn btn-primary">Submit</button>
                            </div>
                        </form>

                        <Alert style={{ visibility: ackVisibilityState }} bsStyle="success">
                            <strong>Appraisal Submitted</strong>
                        </Alert>
                    </Tab>
                </Tabs>
            </div>
        );
    }
}

function mapStateToProps(state) {
    const { reAuthentication } = state;
    const { user } = reAuthentication;
    const userId = state.reAuthentication.user.id;

    return {
        user,
        userId
    };
}

const connectedREHomePage = connect(mapStateToProps)(REHomePage);
export { connectedREHomePage as REHomePage };
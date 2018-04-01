import React from 'react';
import { Link } from 'react-router-dom';
import { connect } from 'react-redux';
import { FormGroup, ControlLabel, FormControl, HelpBlock, Tabs, Tab, Alert } from 'react-bootstrap';

import { userActions, mbrActions } from '../_actions';

class MBRHomePage extends React.Component {

    constructor(props) {
        super(props);

        this.handleChange = this.handleChange.bind(this);

        this.state = {
            name: '',
            mortgageVal: '',
            mortgageInsuranceId: '',
            submittedForm: false,
            requestId: '',
            submittedRequest: false
        };

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmitForm = this.handleSubmitForm.bind(this);
        this.handleGetStatus = this.handleGetStatus.bind(this);
    }

    handleChange(e) {
        const { name, value } = e.target;
        this.setState({ [name]: value });
    }

    handleSubmitForm(e) {
        e.preventDefault();

        const { userId } = this.props;

        this.setState({ submittedForm: true });
        const { name, mortgageVal, mortgageInsuranceId } = this.state;
        const { dispatch } = this.props;
        if (name && mortgageVal && mortgageInsuranceId) {
            dispatch(mbrActions.submit(userId, name, mortgageVal, mortgageInsuranceId));
        }
    }

    handleGetStatus(e) {
        e.preventDefault();

        this.setState({ submittedRequest: true });
        const { requestId } = this.state;
        const { dispatch } = this.props;
        if (requestId) {
            dispatch(mbrActions.getStatus(requestId));
        }
    }

    render() {
        const visibilityState = this.state.submittedRequest ? "visible" : "hidden";
        const { user } = this.props;
        const { status } = this.props;
        const { name, mortgageVal, mortgageInsuranceId, submittedForm, requestId, submittedRequest } = this.state;
        return (
            <div>
                <h1>MBR</h1>
                <p>
                    <Link to="/mbr/login">Logout</Link>
                </p>
                <Tabs defaultActiveKey={1} id="mbr-tabs">
                    <Tab eventKey={1} title="New Request">
                        <form name="form" onSubmit={this.handleSubmitForm}>
                            <div className={'form-group' + (submittedForm && !name ? ' has-error' : '')}>
                                <label htmlFor="name">Name</label>
                                <input type="text" className="form-control" name="name" value={name} onChange={this.handleChange} />
                                {submittedForm && !name &&
                                    <div className="help-block">Name is required</div>
                                }
                            </div>
                            <div className={'form-group' + (submittedForm && !mortgageVal ? ' has-error' : '')}>
                                <label htmlFor="mortgageVal">Mortgage Value ($)</label>
                                <input type="mortgageVal" className="form-control" name="mortgageVal" value={mortgageVal} onChange={this.handleChange} />
                                {submittedForm && !mortgageVal &&
                                    <div className="help-block">Mortgage value is required</div>
                                }
                            </div>
                            <div className={'form-group' + (submittedForm && !mortgageInsuranceId ? ' has-error' : '')}>
                                <label htmlFor="mortgageInsuranceId">Mortgage Insurance ID</label>
                                <input type="text" className="form-control" name="mortgageInsuranceId" value={mortgageInsuranceId} onChange={this.handleChange} />
                                {submittedForm && !mortgageInsuranceId &&
                                    <div className="help-block">Mortgage Insurance ID is required</div>
                                }
                            </div>
                            <div className="form-group">
                                <button className="btn btn-primary">Submit</button>
                            </div>
                        </form>
                    </Tab>
                    <Tab eventKey={2} title="Request Status">
                        <form name="form" onSubmit={this.handleGetStatus}>
                            <div className={'form-group' + (submittedRequest && !requestId ? ' has-error' : '')}>
                                <label htmlFor="requestId">Request ID</label>
                                <input type="text" className="form-control" name="requestId" value={requestId} onChange={this.handleChange} />
                                {submittedRequest && !requestId &&
                                    <div className="help-block">Request ID is required</div>
                                }
                            </div>
                            <div className="form-group">
                                <button className="btn btn-primary">Get Status</button>
                            </div>
                        </form>
                        <Alert style={{visibility: visibilityState}} bsStyle="success">
                            <strong>Request status:</strong>
                            <p>Employer: { status.emp }</p>
                            <p>Insurance: { status.ins }</p>
                        </Alert>
                    </Tab>
                </Tabs>
            </div>
        );
    }
}

function mapStateToProps(state) {
    const { authentication } = state;
    const { user } = authentication;
    const userId = state.authentication.user.id;
    const status = state.mbr.status;
    return {
        user,
        userId,
        status
    };
}

const connectedMBRHomePage = connect(mapStateToProps)(MBRHomePage);
export { connectedMBRHomePage as MBRHomePage };
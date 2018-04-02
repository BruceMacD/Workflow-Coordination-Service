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
            submittedStatusRequest: false
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

        this.setState({ submittedStatusRequest: true });
        const { userId } = this.props;
        const { dispatch } = this.props;
        if (userId) {
            dispatch(mbrActions.getStatus(userId));
        }
    }

    render() {
        const requestVisibilityState = this.state.submittedStatusRequest ? "visible" : "hidden";
        const mbrVisibilityState = this.state.submittedForm ? "visible" : "hidden";
        
        const { user } = this.props;
        const { empStatus } = this.props;
        const { insStatus } = this.props;
        const { munStatus } = this.props;
        const { applicationId } = this.props;
        const { name, mortgageVal, mortgageInsuranceId, submittedForm, requestId, submittedStatusRequest } = this.state;
        
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

                        <Alert style={{visibility: mbrVisibilityState}} bsStyle="success">
                            <strong>Request Submitted</strong>
                            <p>MBR Request ID: { applicationId }</p>
                        </Alert>
                    </Tab>
                    <Tab eventKey={2} title="Request Status">
                        <p></p>
                        <form name="form" onSubmit={this.handleGetStatus}>
                            <div className="form-group">
                                <button className="btn btn-primary">Get Status</button>
                            </div>
                        </form>
                        <Alert style={{visibility: requestVisibilityState}} bsStyle="success">
                            <strong>Request Status</strong>
                            <p>Employer: { empStatus }</p>
                            <p>Insurance: { insStatus }</p>
                            <p>Municipality: { munStatus }</p>
                        </Alert>
                    </Tab>
                </Tabs>
            </div>
        );
    }
}

function mapStateToProps(state) {
    const { mbrAuthentication } = state;
    const { user } = mbrAuthentication;
    const userId = state.mbrAuthentication.user.id;
    
    let status = state.mbr.application;
    const empStatus = status.empInfo ? 'CONFIRMED' : 'PENDING';
    const insStatus = status.insInfo ? 'CONFIRMED' : 'PENDING';
    const munStatus = status.munInfo ? 'CONFIRMED' : 'PENDING';

    let application = state.mbr.application.application;
    const applicationId = application ? application.mortgageId : '';
    return {
        user,
        userId,
        empStatus,
        insStatus,
        munStatus,
        applicationId
    };
}

const connectedMBRHomePage = connect(mapStateToProps)(MBRHomePage);
export { connectedMBRHomePage as MBRHomePage };
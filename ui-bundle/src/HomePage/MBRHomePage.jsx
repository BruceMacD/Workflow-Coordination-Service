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
            dispatchedForm: false,
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
            this.setState({ dispatchedForm: true });
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
        const mbrVisibilityState = this.state.dispatchedForm ? "visible" : "hidden";
        let docVisibilityState = "hidden";
        
        const { user } = this.props;
        const { mbrName } = this.props;
        const { empStatus, insStatus, munStatus } = this.props;
        const { applicationId, applicationName, applicationInsId, applicationVal, empId, empStartDate, empSalary, servicesCode, insuredValue, deductible } = this.props;
        const { name, mortgageVal, mortgageInsuranceId, submittedForm, requestId, submittedStatusRequest } = this.state;
        
        if (empStatus === "CONFIRMED" && insStatus === "CONFIRMED" && munStatus === "CONFIRMED") {
            docVisibilityState = "visible";
        }
        
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
                        <Alert style={{visibility: docVisibilityState}} bsStyle="info">
                            <strong>Mortgage Document</strong>
                            <p>Name: { applicationName }</p>
                            <p>Mortgage ID: { applicationId }</p>
                            <p>Mortgage Insurance ID: { applicationInsId }</p>
                            <p>Mortgage Value: ${ applicationVal }</p>
                            <p></p>
                            <p>Employee ID: { empId }</p>
                            <p>Employment Start Date: { empStartDate }</p>
                            <p>Salary: ${ empSalary }</p>
                            <p></p>
                            <p>Services Code: { servicesCode }</p>
                            <p></p>
                            <p>Insured Value: ${ insuredValue }</p>
                            <p>Deductible: ${ deductible }</p>
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
    
    const status = state.mbr.application;
    const empStatus = status.empInfo ? 'CONFIRMED' : 'PENDING';
    const insStatus = status.insInfo ? 'CONFIRMED' : 'PENDING';
    const munStatus = status.munInfo ? 'CONFIRMED' : 'PENDING';

    let application = state.mbr.application.application;
    const applicationId = application ? application.mortgageId : '';
    const applicationName = application ? application.name : '';
    const applicationInsId = application ? application.mortgageInsuranceId : '';
    const applicationVal = application ? application.mortgageVal : '';

    let empInfo = state.mbr.application.empInfo;
    const empId = empInfo ? empInfo.id : '';
    const empStartDate = empInfo ? empInfo.employmentStartDate : '';
    const empSalary = empInfo ? empInfo.salary : '';

    let munInfo = state.mbr.application.munInfo;
    const servicesCode = munInfo ? munInfo.servicesCode : '';
    
    let insInfo = state.mbr.application.insInfo;
    const insuredValue = insInfo ? insInfo.insuredValue : '';
    const deductible = insInfo ? insInfo.deductible : '';

    return {
        user,
        userId,
        application,
        empStatus,
        insStatus,
        munStatus,
        applicationId,
        applicationName,
        applicationInsId,
        applicationVal,
        empId,
        empStartDate,
        empSalary,
        servicesCode,
        insuredValue,
        deductible
    };
}

const connectedMBRHomePage = connect(mapStateToProps)(MBRHomePage);
export { connectedMBRHomePage as MBRHomePage };
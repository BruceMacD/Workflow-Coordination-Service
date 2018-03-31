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
            mortgageValue: '',
            houseId: '',
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

        this.setState({ submittedForm: true });
        const { name, mortgageValue, houseId } = this.state;
        const { dispatch } = this.props;
        if (name && mortgageValue && houseId) {
            dispatch(mbrActions.submit(name, mortgageValue, houseId));
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
        const { name, mortgageValue, houseId, submittedForm, requestId, submittedRequest } = this.state;
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
                            <div className={'form-group' + (submittedForm && !mortgageValue ? ' has-error' : '')}>
                                <label htmlFor="mortgageValue">Mortgage Value ($)</label>
                                <input type="mortgageValue" className="form-control" name="mortgageValue" value={mortgageValue} onChange={this.handleChange} />
                                {submittedForm && !mortgageValue &&
                                    <div className="help-block">Mortgage value is required</div>
                                }
                            </div>
                            <div className={'form-group' + (submittedForm && !houseId ? ' has-error' : '')}>
                                <label htmlFor="houseId">House ID</label>
                                <input type="text" className="form-control" name="houseId" value={houseId} onChange={this.handleChange} />
                                {submittedForm && !houseId &&
                                    <div className="help-block">House ID is required</div>
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
    const status = state.mbr.status;
    return {
        user,
        status
    };
}

const connectedMBRHomePage = connect(mapStateToProps)(MBRHomePage);
export { connectedMBRHomePage as MBRHomePage };
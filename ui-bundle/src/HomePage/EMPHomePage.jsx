import React from 'react';
import { Link } from 'react-router-dom';
import { connect } from 'react-redux';
import { FormGroup, ControlLabel, FormControl, HelpBlock, Tabs, Tab, Alert } from 'react-bootstrap';

import { userActions, empActions } from '../_actions';

class EMPHomePage extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            submittedForm: false
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
        // const { name, mortgageVal, mortgageInsuranceId } = this.state;
        // const { dispatch } = this.props;
        // if (name && mortgageVal && mortgageInsuranceId) {
        //     dispatch(empActions.submit(userId, name, mortgageVal, mortgageInsuranceId));
        // }
    }

    render() {
        // const requestVisibilityState = this.state.submittedStatusRequest ? "visible" : "hidden";
        
        const { user } = this.props;
        const { submittedForm } = this.state;
        
        return (
            <div>
                <h1>EMP</h1>
                <p>
                    <Link to="/emp/login">Logout</Link>
                </p>
                <Tabs defaultActiveKey={1} id="emp-tabs">
                    <Tab eventKey={1} title="New Request">
                        <form name="form" onSubmit={this.handleSubmitForm}>
                            <div className="form-group">
                                <button className="btn btn-primary">Submit</button>
                            </div>
                        </form>

                        {/* <Alert style={{visibility: mbrVisibilityState}} bsStyle="success">
                            <strong>Request Submitted</strong>
                            <p>MBR Request ID: { applicationId }</p>
                        </Alert> */}
                    </Tab>
                    <Tab eventKey={2} title="Request Status">
                        <p></p>
                    </Tab>
                </Tabs>
            </div>
        );
    }
}

function mapStateToProps(state) {
    const { empAuthentication } = state;
    const { user } = empAuthentication;
    const userId = state.empAuthentication.user.id;

    return {
        user,
        userId
    };
}

const connectedEMPHomePage = connect(mapStateToProps)(EMPHomePage);
export { connectedEMPHomePage as EMPHomePage };
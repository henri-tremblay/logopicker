import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './logo.reducer';
import { ILogo } from 'app/shared/model/logo.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ILogoDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: number }> {}

export class LogoDetail extends React.Component<ILogoDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { logoEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Logo [<b>{logoEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="cloud">Cloud</span>
            </dt>
            <dd>{logoEntity.cloud}</dd>
            <dt>
              <span id="name">Name</span>
            </dt>
            <dd>{logoEntity.name}</dd>
            <dt>
              <span id="url">Url</span>
            </dt>
            <dd>{logoEntity.url}</dd>
          </dl>
          <Button tag={Link} to="/entity/logo" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/logo/${logoEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ logo }: IRootState) => ({
  logoEntity: logo.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(LogoDetail);

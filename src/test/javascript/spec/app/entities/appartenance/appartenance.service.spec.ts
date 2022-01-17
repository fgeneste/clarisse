/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';
import dayjs from 'dayjs';

import { DATE_FORMAT } from '@/shared/date/filters';
import AppartenanceService from '@/entities/appartenance/appartenance.service';
import { Appartenance } from '@/shared/model/appartenance.model';

const error = {
  response: {
    status: null,
    data: {
      type: null,
    },
  },
};

const axiosStub = {
  get: sinon.stub(axios, 'get'),
  post: sinon.stub(axios, 'post'),
  put: sinon.stub(axios, 'put'),
  patch: sinon.stub(axios, 'patch'),
  delete: sinon.stub(axios, 'delete'),
};

describe('Service Tests', () => {
  describe('Appartenance Service', () => {
    let service: AppartenanceService;
    let elemDefault;
    let currentDate: Date;

    beforeEach(() => {
      service = new AppartenanceService();
      currentDate = new Date();
      elemDefault = new Appartenance(
        123,
        'AAAAAAA',
        currentDate,
        currentDate,
        currentDate,
        'AAAAAAA',
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            dateDebut: dayjs(currentDate).format(DATE_FORMAT),
            dateFin: dayjs(currentDate).format(DATE_FORMAT),
            dateElection: dayjs(currentDate).format(DATE_FORMAT),
          },
          elemDefault
        );
        axiosStub.get.resolves({ data: returnedFromService });

        return service.find(123).then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });

      it('should not find an element', async () => {
        axiosStub.get.rejects(error);
        return service
          .find(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should create a Appartenance', async () => {
        const returnedFromService = Object.assign(
          {
            id: 123,
            dateDebut: dayjs(currentDate).format(DATE_FORMAT),
            dateFin: dayjs(currentDate).format(DATE_FORMAT),
            dateElection: dayjs(currentDate).format(DATE_FORMAT),
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dateDebut: currentDate,
            dateFin: currentDate,
            dateElection: currentDate,
          },
          returnedFromService
        );

        axiosStub.post.resolves({ data: returnedFromService });
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a Appartenance', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a Appartenance', async () => {
        const returnedFromService = Object.assign(
          {
            matricule: 'BBBBBB',
            dateDebut: dayjs(currentDate).format(DATE_FORMAT),
            dateFin: dayjs(currentDate).format(DATE_FORMAT),
            dateElection: dayjs(currentDate).format(DATE_FORMAT),
            observation: 'BBBBBB',
            departement: 1,
            libelle: 'BBBBBB',
            article: 'BBBBBB',
            motifDeDebut: 'BBBBBB',
            commentaireDeChangement: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateDebut: currentDate,
            dateFin: currentDate,
            dateElection: currentDate,
          },
          returnedFromService
        );
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a Appartenance', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a Appartenance', async () => {
        const patchObject = Object.assign(
          {
            matricule: 'BBBBBB',
            observation: 'BBBBBB',
            departement: 1,
            article: 'BBBBBB',
          },
          new Appartenance()
        );
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            dateDebut: currentDate,
            dateFin: currentDate,
            dateElection: currentDate,
          },
          returnedFromService
        );
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a Appartenance', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of Appartenance', async () => {
        const returnedFromService = Object.assign(
          {
            matricule: 'BBBBBB',
            dateDebut: dayjs(currentDate).format(DATE_FORMAT),
            dateFin: dayjs(currentDate).format(DATE_FORMAT),
            dateElection: dayjs(currentDate).format(DATE_FORMAT),
            observation: 'BBBBBB',
            departement: 1,
            libelle: 'BBBBBB',
            article: 'BBBBBB',
            motifDeDebut: 'BBBBBB',
            commentaireDeChangement: 'BBBBBB',
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dateDebut: currentDate,
            dateFin: currentDate,
            dateElection: currentDate,
          },
          returnedFromService
        );
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve().then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of Appartenance', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a Appartenance', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a Appartenance', async () => {
        axiosStub.delete.rejects(error);

        return service
          .delete(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });
    });
  });
});

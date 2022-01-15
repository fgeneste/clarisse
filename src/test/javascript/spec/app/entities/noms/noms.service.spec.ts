/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';
import dayjs from 'dayjs';

import { DATE_FORMAT } from '@/shared/date/filters';
import NomsService from '@/entities/noms/noms.service';
import { Noms } from '@/shared/model/noms.model';

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
  describe('Noms Service', () => {
    let service: NomsService;
    let elemDefault;
    let currentDate: Date;

    beforeEach(() => {
      service = new NomsService();
      currentDate = new Date();
      elemDefault = new Noms(
        123,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        currentDate
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            dateDebut: dayjs(currentDate).format(DATE_FORMAT),
            dateFin: dayjs(currentDate).format(DATE_FORMAT),
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

      it('should create a Noms', async () => {
        const returnedFromService = Object.assign(
          {
            id: 123,
            dateDebut: dayjs(currentDate).format(DATE_FORMAT),
            dateFin: dayjs(currentDate).format(DATE_FORMAT),
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dateDebut: currentDate,
            dateFin: currentDate,
          },
          returnedFromService
        );

        axiosStub.post.resolves({ data: returnedFromService });
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a Noms', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a Noms', async () => {
        const returnedFromService = Object.assign(
          {
            qualite: 'BBBBBB',
            nomUsuel: 'BBBBBB',
            prenomUsuel: 'BBBBBB',
            nomPatronymique: 'BBBBBB',
            prenomCivil: 'BBBBBB',
            nomMarital: 'BBBBBB',
            nomDistinctif: 'BBBBBB',
            nomMajuscule: 'BBBBBB',
            nomTechnique: 'BBBBBB',
            feminisation: 'BBBBBB',
            dateDebut: dayjs(currentDate).format(DATE_FORMAT),
            dateFin: dayjs(currentDate).format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateDebut: currentDate,
            dateFin: currentDate,
          },
          returnedFromService
        );
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a Noms', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a Noms', async () => {
        const patchObject = Object.assign(
          {
            qualite: 'BBBBBB',
            prenomUsuel: 'BBBBBB',
            prenomCivil: 'BBBBBB',
            nomMarital: 'BBBBBB',
            nomDistinctif: 'BBBBBB',
            nomMajuscule: 'BBBBBB',
            nomTechnique: 'BBBBBB',
          },
          new Noms()
        );
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            dateDebut: currentDate,
            dateFin: currentDate,
          },
          returnedFromService
        );
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a Noms', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of Noms', async () => {
        const returnedFromService = Object.assign(
          {
            qualite: 'BBBBBB',
            nomUsuel: 'BBBBBB',
            prenomUsuel: 'BBBBBB',
            nomPatronymique: 'BBBBBB',
            prenomCivil: 'BBBBBB',
            nomMarital: 'BBBBBB',
            nomDistinctif: 'BBBBBB',
            nomMajuscule: 'BBBBBB',
            nomTechnique: 'BBBBBB',
            feminisation: 'BBBBBB',
            dateDebut: dayjs(currentDate).format(DATE_FORMAT),
            dateFin: dayjs(currentDate).format(DATE_FORMAT),
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dateDebut: currentDate,
            dateFin: currentDate,
          },
          returnedFromService
        );
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve().then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of Noms', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a Noms', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a Noms', async () => {
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

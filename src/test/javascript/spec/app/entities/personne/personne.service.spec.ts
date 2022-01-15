/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';
import dayjs from 'dayjs';

import { DATE_FORMAT } from '@/shared/date/filters';
import PersonneService from '@/entities/personne/personne.service';
import { Personne } from '@/shared/model/personne.model';

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
  describe('Personne Service', () => {
    let service: PersonneService;
    let elemDefault;
    let currentDate: Date;

    beforeEach(() => {
      service = new PersonneService();
      currentDate = new Date();
      elemDefault = new Personne(
        123,
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        0,
        currentDate,
        'AAAAAAA',
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            dateDeNaissance: dayjs(currentDate).format(DATE_FORMAT),
            dateDeDeces: dayjs(currentDate).format(DATE_FORMAT),
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

      it('should create a Personne', async () => {
        const returnedFromService = Object.assign(
          {
            id: 123,
            dateDeNaissance: dayjs(currentDate).format(DATE_FORMAT),
            dateDeDeces: dayjs(currentDate).format(DATE_FORMAT),
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dateDeNaissance: currentDate,
            dateDeDeces: currentDate,
          },
          returnedFromService
        );

        axiosStub.post.resolves({ data: returnedFromService });
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a Personne', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a Personne', async () => {
        const returnedFromService = Object.assign(
          {
            matricule: 'BBBBBB',
            dateDeNaissance: dayjs(currentDate).format(DATE_FORMAT),
            lieuDeNaissance: 'BBBBBB',
            departementDeNaissance: 1,
            dateDeDeces: dayjs(currentDate).format(DATE_FORMAT),
            lieuDeDeces: 'BBBBBB',
            departementDeDeces: 1,
            profession: 'BBBBBB',
            diplome: 'BBBBBB',
            decoration: 'BBBBBB',
            rangProtocolaire: 1,
            cspInsee: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateDeNaissance: currentDate,
            dateDeDeces: currentDate,
          },
          returnedFromService
        );
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a Personne', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a Personne', async () => {
        const patchObject = Object.assign(
          {
            matricule: 'BBBBBB',
            departementDeNaissance: 1,
            lieuDeDeces: 'BBBBBB',
            profession: 'BBBBBB',
            decoration: 'BBBBBB',
            cspInsee: 'BBBBBB',
          },
          new Personne()
        );
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            dateDeNaissance: currentDate,
            dateDeDeces: currentDate,
          },
          returnedFromService
        );
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a Personne', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of Personne', async () => {
        const returnedFromService = Object.assign(
          {
            matricule: 'BBBBBB',
            dateDeNaissance: dayjs(currentDate).format(DATE_FORMAT),
            lieuDeNaissance: 'BBBBBB',
            departementDeNaissance: 1,
            dateDeDeces: dayjs(currentDate).format(DATE_FORMAT),
            lieuDeDeces: 'BBBBBB',
            departementDeDeces: 1,
            profession: 'BBBBBB',
            diplome: 'BBBBBB',
            decoration: 'BBBBBB',
            rangProtocolaire: 1,
            cspInsee: 'BBBBBB',
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dateDeNaissance: currentDate,
            dateDeDeces: currentDate,
          },
          returnedFromService
        );
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve().then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of Personne', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a Personne', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a Personne', async () => {
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

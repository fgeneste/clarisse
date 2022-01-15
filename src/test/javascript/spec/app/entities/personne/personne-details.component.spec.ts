/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import PersonneDetailComponent from '@/entities/personne/personne-details.vue';
import PersonneClass from '@/entities/personne/personne-details.component';
import PersonneService from '@/entities/personne/personne.service';
import router from '@/router';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Personne Management Detail Component', () => {
    let wrapper: Wrapper<PersonneClass>;
    let comp: PersonneClass;
    let personneServiceStub: SinonStubbedInstance<PersonneService>;

    beforeEach(() => {
      personneServiceStub = sinon.createStubInstance<PersonneService>(PersonneService);

      wrapper = shallowMount<PersonneClass>(PersonneDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { personneService: () => personneServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundPersonne = { id: 123 };
        personneServiceStub.find.resolves(foundPersonne);

        // WHEN
        comp.retrievePersonne(123);
        await comp.$nextTick();

        // THEN
        expect(comp.personne).toBe(foundPersonne);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundPersonne = { id: 123 };
        personneServiceStub.find.resolves(foundPersonne);

        // WHEN
        comp.beforeRouteEnter({ params: { personneId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.personne).toBe(foundPersonne);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});

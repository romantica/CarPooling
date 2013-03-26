import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.*;
import static play.test.Helpers.HTMLUNIT;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;

import play.libs.F.Callback;
import play.test.TestBrowser;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;

import com.avaje.ebean.Ebean;

import controllers.Matching;

import models.objects.*;

public class MatchingTest
{
	
	@Test
	public void testMatch()
	{
		//fail("Not yet implemented");
		//TODO
		running(testServer(3333, fakeApplication(inMemoryDatabase())),
				HTMLUNIT, new Callback<TestBrowser>() {
					public void invoke(TestBrowser browser) {
		
		Proposal proposal1 = new Proposal(1.0F, 1, null, null);
		Proposal proposal2 = new Proposal(2.0F, 1, null, null);
		Proposal proposal3 = new Proposal(0F, 2, null, null);
		Coordinate coord1 = new Coordinate(0.0, 0.0);
		Coordinate coord2 = new Coordinate(1.0, 1.0);
		Coordinate coord3 = new Coordinate(2.0, 2.0);
		Coordinate coord4 = new Coordinate(3.0, 3.0);
		PickupPoint pup1 = new PickupPoint(null, null, null, coord1);		
		PickupPoint pup2 = new PickupPoint(null, null, null, coord2);		
		PickupPoint pup3 = new PickupPoint(null, null, null, coord3);
		PickupPoint pup4 = new PickupPoint(null, null, null, coord4);
		pup1.save();
		//System.out.println(PickupPoint.find.where().eq("id", pup1.getId()).findUnique().getCoordinateX());
		pup2.save();
		pup3.save();
		pup4.save();
		Date date11 = new Date(1000);
		Date date12 = new Date(2000);
		Date date21 = new Date(1000000);
		Date date22 = new Date(1001000);
		Itinerary itinerary1 = new Itinerary(date11, date12, pup1);
		Itinerary itinerary2 = new Itinerary(date21, date22, pup2);
		itinerary1.save();
		//System.out.println("$$$$$$$" + Itinerary.find.where().eq("id", itinerary1.getId()).findUnique().getPickupPoint().getCoordinateX());
		itinerary2.save();
		proposal1.addItinerary(itinerary1);
		proposal1.addItinerary(itinerary2);
		proposal2.addItinerary(itinerary1);
		proposal2.addItinerary(itinerary2);
		proposal3.addItinerary(itinerary1);
		proposal3.addItinerary(itinerary2);
		
		proposal1.save();
		proposal2.save();
		proposal3.save();
		//System.out.println("$$$$$$$" + Proposal.find.where().eq("id", proposal1.getId()).findUnique().getItinerary().getFirst().getPickupPoint().getCoordinateX());
		
		//sièges
		Request request = new Request(coord1, coord2, null, null, date21, 2, 0, 0, 0, null, null);
		System.out.println("Match result : " + Matching.match(request));
		//assertEquals(Matching.match(request).get(0).getProposal(), proposal3);
					}});
	}
	
	@Test
	public void testGetProposals() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())),
				HTMLUNIT, new Callback<TestBrowser>() {
					public void invoke(TestBrowser browser) {

						Proposal p1 = new Proposal(1, 1, null, null);
						p1.save();
						Proposal p2 = new Proposal(2, 2, null, null);
						p2.save();
						Proposal p3 = new Proposal(3, 3, null, null);
						p3.save();
						User user = new User("1", "1", "1", "1", "1", 0, null,
								null, null, null, null);
						user.save();
						Car car = new Car("m1", "b1", "p1", user);
						car.save();
						p1.setCar(car);
						p1.setUser(user);
						p1.save();

						ArrayList<Proposal> ps = Matching.getProposals();

						assertThat(ps.get(0).getId()).isEqualTo(1);
						assertThat(ps.get(0).getKmCost()).isEqualTo(1);
						assertThat(ps.get(0).getAvailableSeats()).isEqualTo(1);
						assertThat(ps.get(0).getUser().getLogin()).isEqualTo(
								user.getLogin());
						assertThat(ps.get(0).getCar().getModel()).isEqualTo(
								car.getModel());

						assertThat(ps.get(1).getId()).isEqualTo(2);
						assertThat(ps.get(1).getKmCost()).isEqualTo(2);
						assertThat(ps.get(1).getAvailableSeats()).isEqualTo(2);

						assertThat(ps.get(2).getId()).isEqualTo(3);
						assertThat(ps.get(2).getKmCost()).isEqualTo(3);
						assertThat(ps.get(2).getAvailableSeats()).isEqualTo(3);
					}
				});
	}	

	@Test
	public void testIsArrivalTimeAMatch()
	{
		Proposal proposal = new Proposal(1.0F, 1, null, null);
		Coordinate coord1 = new Coordinate(0.0, 0.0);
		Coordinate coord2 = new Coordinate(1.0, 1.0);
		PickupPoint pup1 = new PickupPoint(null, null, null, coord1);
		PickupPoint pup2 = new PickupPoint(null, null, null, coord2);
		Date date11 = new Date(1000);
		Date date12 = new Date(2000);
		Date date21 = new Date(1000000);
		Date date22 = new Date(1001000);
		Itinerary itinerary1 = new Itinerary(date11, date12, pup1);
		Itinerary itinerary2 = new Itinerary(date21, date22, pup2);
		proposal.addItinerary(itinerary1);
		proposal.addItinerary(itinerary2);
		Request request = new Request(coord1, coord2, null, null, null, 1, 0, 0, 0, null, null);
		
		request.setArrivalTime(new Date(5000));
		assertTrue(Matching.isArrivalTimeAMatch(proposal, request));
		request.setArrivalTime(new Date(1000));
		assertTrue(Matching.isArrivalTimeAMatch(proposal, request));
		request.setArrivalTime(new Date(2000));
		assertTrue(Matching.isArrivalTimeAMatch(proposal, request));
		request.setArrivalTime(new Date(1000000));
		assertTrue(Matching.isArrivalTimeAMatch(proposal, request));
		request.setArrivalTime(new Date(1001000));
		assertTrue(Matching.isArrivalTimeAMatch(proposal, request));
		
		request.setArrivalTime(new Date(0));
		assertFalse(Matching.isArrivalTimeAMatch(proposal, request));
		request.setToleranceTime(2000);
		assertTrue(Matching.isArrivalTimeAMatch(proposal, request));
		
		request.setArrivalTime(new Date(2000000));
		assertFalse(Matching.isArrivalTimeAMatch(proposal, request));
		request.setToleranceTime(1000000);
		assertTrue(Matching.isArrivalTimeAMatch(proposal, request));
	}

	@Test
	public void testIsPriceAMatch()
	{
		Proposal proposal = new Proposal(1.0F, 1, null, null);
		Coordinate coord1 = new Coordinate(0.0, 0.0);
		Coordinate coord2 = new Coordinate(1.0, 1.0);
		PickupPoint pup1 = new PickupPoint(null, null, null, coord1);
		PickupPoint pup2 = new PickupPoint(null, null, null, coord2);
		Date date11 = new Date(1000);
		Date date12 = new Date(2000);
		Date date21 = new Date(1000000);
		Date date22 = new Date(1001000);
		Itinerary itinerary1 = new Itinerary(date11, date12, pup1);
		Itinerary itinerary2 = new Itinerary(date21, date22, pup2);
		proposal.addItinerary(itinerary1);
		proposal.addItinerary(itinerary2);
		Request request = new Request(coord1, coord2, null, null, null, 0, 0, 0, 1.0F, null, null);
		
		assertFalse(Matching.isPriceAMatch(proposal, request, Matching.getPickupPoints(proposal, request)));
		
		request.setTolerancePrice(200.0F);
		assertTrue(Matching.isPriceAMatch(proposal, request, Matching.getPickupPoints(proposal, request)));
	}

	@Test
	public void testIsSeatsRequestAMatch()
	{
		Proposal proposal = new Proposal(1.0F, 1, null, null);
		proposal.addItinerary(null);
		proposal.addItinerary(null);
		Request request = new Request(null, null, null, null, null, 1, 0, 0, 0, null, null);
		
		assertTrue(Matching.isSeatsRequestAMatch(proposal, request));
		
		request.setNecessarySeats(2);
		assertFalse(Matching.isSeatsRequestAMatch(proposal, request));
	}

	@Test
	public void testGetDateAndTolerance()
	{
		Date date1 = new Date(1000);
		
		assertEquals(Matching.getDateAndTolerance(date1, 100, true).getTime(), 1100, 0);
		assertEquals(Matching.getDateAndTolerance(date1, 100, false).getTime(), 900, 0);
	}

	@Test
	public void testIsTimingAMatch()
	{
		Proposal proposal = new Proposal(1.0F, 1, null, null);
		Coordinate coord0 = new Coordinate(-0.1, -0.1);
		Coordinate coord1 = new Coordinate(0.0, 0.0);
		Coordinate coord2 = new Coordinate(1.0, 1.0);
		Coordinate coord3 = new Coordinate(1.1, 1.1);
		PickupPoint pup1 = new PickupPoint(null, null, null, coord1);
		PickupPoint pup2 = new PickupPoint(null, null, null, coord2);
		Date date11 = new Date(1000);
		Date date12 = new Date(2000);
		Date date21 = new Date(1000000);
		Date date22 = new Date(1001000);
		Itinerary itinerary1 = new Itinerary(date11, date12, pup1);
		Itinerary itinerary2 = new Itinerary(date21, date22, pup2);
		proposal.addItinerary(itinerary1);
		proposal.addItinerary(itinerary2);
		Request request = new Request(coord0, coord3, null, null, new Date(1000000), 0, 1000, 40000, 0, null, null);
		
		assertFalse(Matching.isTimingAMatch(proposal, request, Matching.getPickupPoints(proposal, request)));
		
		request.setToleranceTime(18000000);
		assertTrue(Matching.isTimingAMatch(proposal, request, Matching.getPickupPoints(proposal, request)));
	}

	@Test
	public void testSortByTime()
	{
		Traject t1 = new Traject(0, 0, null, null, new Composition(true, new Date(1000), null), null, null);
		Traject t2 = new Traject(0, 0, null, null, new Composition(true, new Date(2000), null), null, null);
		Traject t3 = new Traject(0, 0, null, null, new Composition(true, new Date(3000), null), null, null);
		Traject t4 = new Traject(0, 0, null, null, new Composition(true, new Date(4000), null), null, null);
		Traject t5 = new Traject(0, 0, null, null, new Composition(true, new Date(5000), null), null, null);
		Traject t6 = new Traject(0, 0, null, null, new Composition(true, new Date(6000), null), null, null);
		
		ArrayList<Traject> list1 = new ArrayList<Traject>();
		list1.add(t3);
		list1.add(t4);
		list1.add(t2);
		list1.add(t6);
		list1.add(t1);
		list1.add(t5);
		
		ArrayList<Traject> list2 = new ArrayList<Traject>();
		list2.add(t1);
		list2.add(t2);
		list2.add(t3);
		list2.add(t4);
		list2.add(t5);
		list2.add(t6);
		
		assertEquals(Matching.sortByTime(list1), list2);
	}
	
	/*
	@Test
	public void testSortByDistance()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testSortByPrice()
	{
		fail("Not yet implemented");
	}
	*/
	
	@Test
	public void testDistance()
	{
		Coordinate start = new Coordinate(50.674335, 4.521225);
		Coordinate end = new Coordinate(50.674155, 4.520823);
		
		assertEquals(Matching.distance(start, end), 34.0, 1);
		assertEquals(Matching.distance(start, start), 0.0, 0.0);
	}

	@Test
	public void testGetTrajectLength()
	{
		Proposal proposal = new Proposal(1.0F, 1, null, null);
		Coordinate coord1 = new Coordinate(0.0, 0.0);
		Coordinate coord2 = new Coordinate(1.0, 1.0);
		Coordinate coord3 = new Coordinate(2.0, 2.0);
		Coordinate coord4 = new Coordinate(3.0, 3.0);		
		PickupPoint pup1 = new PickupPoint(null, null, null, coord1);
		PickupPoint pup2 = new PickupPoint(null, null, null, coord2);
		PickupPoint pup3 = new PickupPoint(null, null, null, coord3);
		PickupPoint pup4 = new PickupPoint(null, null, null, coord4);
		Itinerary itinerary1 = new Itinerary(null, null, pup1);
		Itinerary itinerary2 = new Itinerary(null, null, pup2);
		Itinerary itinerary3 = new Itinerary(null, null, pup3);
		Itinerary itinerary4 = new Itinerary(null, null, pup4);
		proposal.addItinerary(itinerary1);
		proposal.addItinerary(itinerary2);
		proposal.addItinerary(itinerary3);
		proposal.addItinerary(itinerary4);
		double distance = 0;
		
		distance += Matching.distance(coord1, coord2);
		distance += Matching.distance(coord2, coord3);
		distance += Matching.distance(coord3, coord4);
		
		assertEquals(Matching.getTrajectLength(proposal.getItinerary(), pup1, pup4), distance, 0);		
	}

	@Test
	public void testGetPickupPoints()
	{
		Proposal proposal = new Proposal(1.0F, 1, null, null);
		Coordinate coord0 = new Coordinate(-1.0, -1.0);
		Coordinate coord1 = new Coordinate(0.0, 0.0);
		Coordinate coord2 = new Coordinate(1.0, 1.0);
		Coordinate coord3 = new Coordinate(2.0, 2.0);
		Coordinate coord4 = new Coordinate(3.0, 3.0);
		Coordinate coord5 = new Coordinate(4.0, 4.0);
		Coordinate coord6 = new Coordinate(0.5, 0.5);
		Coordinate coord7 = new Coordinate(2.5, 2.5);
		PickupPoint pup1 = new PickupPoint(null, null, null, coord1);
		PickupPoint pup2 = new PickupPoint(null, null, null, coord2);
		PickupPoint pup3 = new PickupPoint(null, null, null, coord3);
		PickupPoint pup4 = new PickupPoint(null, null, null, coord4);
		Itinerary itinerary1 = new Itinerary(null, null, pup1);
		Itinerary itinerary2 = new Itinerary(null, null, pup2);
		Itinerary itinerary3 = new Itinerary(null, null, pup3);
		Itinerary itinerary4 = new Itinerary(null, null, pup4);
		proposal.addItinerary(itinerary1);
		proposal.addItinerary(itinerary2);
		proposal.addItinerary(itinerary3);
		proposal.addItinerary(itinerary4);
		
		Request request = new Request(coord0, coord4, null, null, null, 1, 0, 0, 0, null, null);
		assertNull(Matching.getPickupPoints(proposal, request));
		
		request = new Request(coord1, coord5, null, null, null, 1, 0, 0, 0, null, null);
		assertNull(Matching.getPickupPoints(proposal, request));
		
		request = new Request(coord1, coord4, null, null, null, 1, 0, 0, 0, null, null);		
		assertEquals(Matching.getPickupPoints(proposal, request)[0], pup1);
		assertEquals(Matching.getPickupPoints(proposal, request)[1], pup4);
		
		request = new Request(coord2, coord3, null, null, null, 1, 0, 0, 0, null, null);
		assertEquals(Matching.getPickupPoints(proposal, request)[0], pup2);
		assertEquals(Matching.getPickupPoints(proposal, request)[1], pup3);
		
		request = new Request(coord2, coord3, null, null, null, 1, 0, 100000, 0, null, null);
		assertEquals(Matching.getPickupPoints(proposal, request)[0], pup2);
		assertEquals(Matching.getPickupPoints(proposal, request)[1], pup3);
		
		request = new Request(coord0, coord5, null, null, null, 1, 0, 400000, 0, null, null);
		assertEquals(Matching.getPickupPoints(proposal, request)[0], pup1);
		assertEquals(Matching.getPickupPoints(proposal, request)[1], pup4);
		
		request = new Request(coord6, coord7, null, null, null, 1, 0, 300000, 0, null, null);
		assertEquals(Matching.getPickupPoints(proposal, request)[0], pup2);
		assertEquals(Matching.getPickupPoints(proposal, request)[1], pup3);
	}

}

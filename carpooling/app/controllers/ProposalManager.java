public interface ProposalManager{
	
	/**
	 * Retourne une liste de PickupPoints susceptibles d'etre
	 * des points de pour le driver. Sont renvoyes tous les 
	 * PickupPoints presents dans une ellipse autour des points
	 * de depart et d'arrivee.
	 */
	public List<PickupPoint> getPickupPoints(Coordinate start, Coordinate end);
	
	/**
	 * Enregistre une offre (proposal) dans la base de donnees.
	 */
	public void recordProposal(Proposal proposal);

	/**
	 * Recupere l'ensemble des offres d'un utilisateur
	 * dans la base de donnees.
	 */
	public List<Proposal> getProposalList(User user); 

	/**
	 * Remplace l'ancienne offre par la nouvelle dans la base 
	 * de donnees, previent tous les passagers de la suppression 
	 * de leur trajet lie a l'ancienne proposition, (appel de 
	 * cancelTraject de TrajectManager) supprime les trajets 
	 * associes a l'ancienne offre (idem).
	 */
	public void modifyProposal(Proposal oldProposal, Proposal newProposal);
}
//
//  AdDetailsVC.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 2/7/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import UIKit

class AdDetailsVC: BaseTVC, UICollectionViewDelegate,UICollectionViewDataSource,UICollectionViewDelegateFlowLayout {
    
    
    @IBOutlet weak var collectionView : UICollectionView!
    
    //General
    @IBOutlet weak var img : UIImageView!
    @IBOutlet weak var nameLbl : UILabel!
    @IBOutlet weak var priceLbl : UILabel!
    @IBOutlet weak var viewsLbl : UILabel!
    @IBOutlet weak var dateLbl : UILabel!
    @IBOutlet weak var catLbl : UILabel!
    @IBOutlet weak var sellerLbl : UILabel!
    @IBOutlet weak var negotiableLbl : UILabel!
    @IBOutlet weak var desLbl : UILabel!


    
    // 2 Property
    @IBOutlet weak var stateLbl : UILabel!
    @IBOutlet weak var rooms_numLbl : UILabel!
    @IBOutlet weak var floorLbl : UILabel!
    @IBOutlet weak var with_furnitureLbl : UILabel!
    @IBOutlet weak var spaceLbl : UILabel!
    
    // 8 Job
    @IBOutlet weak var education_nameLbl : UILabel!
    @IBOutlet weak var schedule_nameLbl : UILabel!
    
    var ad : AD!
//    var tamplateId : Int = -1
//    var category_full_name : String!
    
    override func viewDidLoad() {
        
        refreshData()
        getData()
    }
    
    
    override func getRefreshing() {
        Communication.shared.get_ad_details(ad_id: self.ad.ad_id.intValue, template_id: self.ad.tamplate_id.intValue) { (res) in
            self.hideLoading()
            self.ad = res
            self.refreshData()
            
        }
    }
    
    func refreshData(){
        
        self.title = ad.title
        Provider.sd_setImage(self.img, urlString: ad.main_image)
        self.nameLbl.text = ad.title
        self.priceLbl.text = ad.price.doubleValue.formatDigital() + "\n S.P"
        self.viewsLbl.text = ad.show_period.stringValue
        self.dateLbl.text = ad.publish_date
        var cat = ""
        cat += (ad.parent_category_name != nil) ? "\(ad.parent_category_name!)-" : ""
        cat += (ad.category_name != nil) ? "\(ad.category_name!)" : ""
        self.catLbl.text = "\(cat)"
        self.sellerLbl.text = ad.seller_name
        self.negotiableLbl.text = ad.is_negotiable.Boolean ? "yes".localized : "no".localized
        self.desLbl.text = ad.description
        
        //gallery ad images
        collectionView.delegate = self
        collectionView.dataSource = self
        self.tableView.rowHeight = UITableViewAutomaticDimension
        self.tableView.estimatedRowHeight = 100
        
        //2 Properties
        if self.ad.tamplate_id.intValue == 2{
            self.stateLbl.text = ad.property.state
            self.rooms_numLbl.text = ad.property.rooms_num
            self.floorLbl.text = ad.property.floor
            self.with_furnitureLbl.text = ad.property.with_furniture
            self.spaceLbl.text = ad.property.space
        }
        
        if self.ad.tamplate_id.intValue == 8{
            self.schedule_nameLbl.text = ad.job.schedule_name
            self.education_nameLbl.text = ad.job.education_name
        }
        
        
        
        
        self.tableView.reloadData()
        self.collectionView.reloadData()
    }
    
    override func tableView(_ tableView: UITableView, willDisplay cell: UITableViewCell, forRowAt indexPath: IndexPath) {
        cell.selectionStyle = .none
    }
    
    
    override func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        switch indexPath.section {
        case 0:
            return (indexPath.row == 0) ? tableView.frame.height / 2 + 110 : UITableViewAutomaticDimension
        default:
            return (self.ad.tamplate_id.intValue == indexPath.section) ? UITableViewAutomaticDimension : 0
        }
    }
    
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return self.ad.images.count
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "cell", for: indexPath) as! CommericalCell
        
//        cell.imageName = "ad\(indexPath.row + 1)"
        cell.im = self.ad.images[indexPath.row]
        
        return cell
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        let h = collectionView.frame.height - 20
        return CGSize.init(width: h, height: h)
    }
    
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        Provider.sd_setImage(self.img, urlString: self.ad.images[indexPath.row].image)
    }

    
    
    
    
}


//
//  AdDetailsVC.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 2/7/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import UIKit
import AFDateHelper
import SwiftyJSON
import Lightbox

class AdDetailsVC: BaseTVC, UICollectionViewDelegate,UICollectionViewDataSource,UICollectionViewDelegateFlowLayout {
    
    @IBOutlet weak var collectionView : UICollectionView!
    @IBOutlet weak var collectionView2 : UICollectionView!
    @IBOutlet weak var collectionViewHeight : NSLayoutConstraint!
    var parentBase: AdDetailsBaseVC?
    var imagesController = LightboxController.init()

    
    //General
    @IBOutlet weak var img : UIImageView!
    @IBOutlet weak var vvPrice : UIView!
    @IBOutlet weak var numberLbl : UILabel!
    @IBOutlet weak var nameLbl : UILabel!
    @IBOutlet weak var priceLbl : UILabel!
//    @IBOutlet weak var viewsLbl : UILabel!
    @IBOutlet weak var dateLbl : UILabel!
    @IBOutlet weak var catLbl : UILabel!
    @IBOutlet weak var sellerLbl : UILabel!
    @IBOutlet weak var negotiableLbl : UILabel!
    @IBOutlet weak var locationLbl : UILabel!
    @IBOutlet weak var desLbl : UILabel!
    
    @IBOutlet weak var favVV : UIView!
    @IBOutlet weak var imgFav : UIImageView!


    // 1 Vehicle
    @IBOutlet weak var manufacture_dateLbl : UILabel!
    @IBOutlet weak var is_automaticLbl : UILabel!
    @IBOutlet weak var is_new1 : UILabel!
    @IBOutlet weak var kilometerLbl : UILabel!
    @IBOutlet weak var type_nameLbl : UILabel!
    @IBOutlet weak var type_model_nameLbl : UILabel!
    
    
    // 2 Property
    @IBOutlet weak var stateLbl : UILabel!
    @IBOutlet weak var rooms_numLbl : UILabel!
    @IBOutlet weak var floorLbl : UILabel!
    @IBOutlet weak var with_furnitureLbl : UILabel!
    @IBOutlet weak var spaceLbl : UILabel!
    
    
    // 3 Mobile
    @IBOutlet weak var is_new3 : UILabel!
    @IBOutlet weak var type_nameLbl3 : UILabel!
    
    // 4 Electronic
    @IBOutlet weak var is_new4 : UILabel!
    @IBOutlet weak var type_nameLbl4 : UILabel!
    
    // 5 Fashion
    @IBOutlet weak var is_new5 : UILabel!
    
    // 6 Kids
    @IBOutlet weak var is_new2 : UILabel!
    
    // 7 Sport
    @IBOutlet weak var is_new7 : UILabel!
    
    
    // 8 Job
    @IBOutlet weak var education_nameLbl : UILabel!
    @IBOutlet weak var schedule_nameLbl : UILabel!
    @IBOutlet weak var experienceLbl : UILabel!
    
    // 9 Industry
    @IBOutlet weak var is_new9 : UILabel!
    
    var selectedIndex : Int = 0
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
            self.parentBase?.ad = res
            
            
//            if !res.images.isEmpty{
                let im = IMG()
                im.image = self.ad.main_image
                self.ad.images.insert(im, at: 0)
//            }
            
            self.parentBase?.refreshBar()
            self.refreshData()
        }
    }
    
    func refreshData(){
        self.title = ad.title
        Provider.sd_setImage(self.img, urlString: ad.main_image)
        
        var number = "#"
        let n = 5 - self.ad.ad_id.stringValue.count
        for _ in 0..<n{
            number += "0"
        }
        number += self.ad.ad_id.stringValue
        self.numberLbl.text = number
        
        self.nameLbl.text = ad.title
        if ad.price.doubleValue == 0{
            self.priceLbl.text = "Free".localized
        }else{
            self.priceLbl.text = ad.price.doubleValue.formatDigital() + "\n " + "S.P".localized
        }
        if ad.publish_date != nil{
            let d = Date.init(fromString: ad.publish_date, format: .custom("yyyy-MM-dd hh:mm:ss"))
            self.dateLbl.text = d?.toString(format: DateFormatType.isoDate)
        }else{
            self.dateLbl.text = nil
        }
        var cat = ""
        cat += (ad.parent_category_name != nil) ? "\(ad.parent_category_name!)-" : ""
        cat += (ad.category_name != nil) ? "\(ad.category_name!)" : ""
        self.catLbl.text = "\(cat)"
        var loc = ""
        loc += (ad.city_name != nil) ? "\(ad.city_name!) - " : ""
        loc += (ad.location_name != nil) ? "\(ad.location_name!)" : ""
        self.locationLbl.text = "\(loc)"
        
        self.sellerLbl.text = ad.seller_name
        self.negotiableLbl.text = ad.is_negotiable.Boolean ? "yes".localized : "no".localized
        self.desLbl.text = ad.description
        
        //gallery ad images
        collectionView.delegate = self
        collectionView.dataSource = self
        self.tableView.rowHeight = UITableViewAutomaticDimension
        self.tableView.estimatedRowHeight = 100
        self.tableView.tableFooterView = UIView()  // it's just 1 line, awesome!
        
        //1 Vehicles
        if self.ad.tamplate_id.intValue == 1{
            
            self.manufacture_dateLbl.text = ad.vehicle.manufacture_date
            if ad.vehicle.is_automatic != nil{
                self.is_automaticLbl.text = ad.vehicle.is_automatic.Boolean ? "Automatic".localized : "Manual".localized
            }
            if ad.vehicle.is_new != nil{
                self.is_new1.text = ad.vehicle.is_new.Boolean ? "new".localized : "old".localized
            }
            if self.ad.vehicle.kilometer != nil{
                self.kilometerLbl.text = ad.vehicle.kilometer.doubleValue.formatDigital()
            }
            self.type_nameLbl.text = ad.vehicle.type_name
            self.type_model_nameLbl.text = ad.vehicle.type_model_name
        }
        
        
        //2 Properties
        if self.ad.tamplate_id.intValue == 2{
            self.stateLbl.text = ad.property.state
            self.rooms_numLbl.text = ad.property.rooms_num
            self.floorLbl.text = ad.property.floor
            if ad.property.with_furniture != nil{
                self.with_furnitureLbl.text = ad.property.with_furniture.Boolean ? "yes".localized : "no".localized
            }
            self.spaceLbl.text = ad.property.space
        }
        
        //3 Mobiles
        if self.ad.tamplate_id.intValue == 3{
            if ad.industry.is_new != nil{
                self.is_new3.text = ad.mobile.is_new.Boolean ? "new".localized : "old".localized
            }
            self.type_nameLbl3.text = ad.mobile.type_name
        }
        
        //4 Electronic
        if self.ad.tamplate_id.intValue == 4{
            if ad.electronic.is_new != nil{
                self.is_new4.text = ad.electronic.is_new.Boolean ? "new".localized : "old".localized
            }
            self.type_nameLbl4.text = ad.electronic.type_name
        }
        
        //5 Fashion
        if self.ad.tamplate_id.intValue == 5{
            if ad.fashion.is_new != nil{
                self.is_new5.text = ad.fashion.is_new.Boolean ? "new".localized : "old".localized
            }
        }
        
        
        //6 Kids
        if self.ad.tamplate_id.intValue == 6,ad.kids.is_new != nil {
            self.is_new2.text = ad.kids.is_new.Boolean ? "new".localized : "old".localized
        }
        
        //7 Fashion
        if self.ad.tamplate_id.intValue == 7{
            if ad.sport.is_new != nil{
                self.is_new7.text = ad.sport.is_new.Boolean ? "new".localized : "old".localized
            }
        }
        
        
        //8 Job
        if self.ad.tamplate_id.intValue == 8{
            self.schedule_nameLbl.text = ad.job.schedule_name
            self.education_nameLbl.text = ad.job.education_name
            self.experienceLbl.text = ad.job.experience
            self.vvPrice.isHidden = true
        }
        
        //9 Industry
        if self.ad.tamplate_id.intValue == 9,ad.industry.is_new != nil{
            self.is_new9.text = ad.industry.is_new.Boolean ? "new".localized : "old".localized
        }
        
        
        // ALL DATA
        if self.ad.images.isEmpty{
            self.collectionViewHeight.constant = 40
            self.collectionView.backgroundColor = .clear
        }else{
            self.collectionViewHeight.constant = 110
            self.collectionView.backgroundColor = .white
        }
        
        if User.isRegistered(),self.ad.seller_id.intValue != User.getID() {
            self.favVV.isHidden = false
            let tap = UITapGestureRecognizer.init(target: self, action: #selector(self.changeFav))
            self.imgFav.addGestureRecognizer(tap)
            self.imgFav.isUserInteractionEnabled = true
            if let is_favorite = self.ad.is_favorite{
                self.imgFav.image = (is_favorite.Boolean) ? #imageLiteral(resourceName: "star") :  #imageLiteral(resourceName: "star_copy")
                self.favVV.backgroundColor = (is_favorite.Boolean) ? Theme.Color.White :  Theme.Color.red
                self.favVV.borderColor = Theme.Color.red
                self.favVV.borderWidth = 1

            }else{
                self.favVV.isHidden = true
            }
        }else{
            self.favVV.isHidden = true
        }

        
        self.tableView.reloadData()
        self.collectionView.reloadData()
        self.collectionView2.reloadData()
    }
    
    
    func refreshImageFav(){
        imgFav.transform = CGAffineTransform(scaleX: 0.1, y: 0.1)
        
        UIView.animate(withDuration: 2.0,
                       delay: 0,
                       usingSpringWithDamping: 0.2,
                       initialSpringVelocity: 6.0,
                       options: .allowUserInteraction,
                       animations: { [weak self] in
                        self?.imgFav.transform = .identity
            }, completion: nil)
        
        self.imgFav.image = (self.ad.is_favorite.Boolean) ? #imageLiteral(resourceName: "star") :  #imageLiteral(resourceName: "star_copy")
        self.favVV.backgroundColor = (self.ad.is_favorite.Boolean) ? Theme.Color.White :  Theme.Color.red
        self.favVV.borderColor = Theme.Color.red
        self.favVV.borderWidth = 1
    }
    
    @objc func changeFav(){
        
        if self.ad.is_favorite.Boolean{
            Communication.shared.remove_from_favorite(self.ad.ad_id.intValue, callback: { (res) in
                self.ad.is_favorite = JSON(false)
                self.refreshImageFav()
            })
            
        }else{
            Communication.shared.set_as_favorite(self.ad.ad_id.intValue, callback: { (res) in
                self.ad.is_favorite = JSON(true)
                self.refreshImageFav()
            })
        }
    }
    
    
    
    override func tableView(_ tableView: UITableView, willDisplay cell: UITableViewCell, forRowAt indexPath: IndexPath) {
        cell.selectionStyle = .none
    }
    
    
    override func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        switch indexPath.section {
        case 0:
            let collectionVHeight : CGFloat = (self.ad.images.isEmpty) ? 40 : 110
            return (indexPath.row == 0) ? tableView.frame.height / 2 + collectionVHeight : UITableViewAutomaticDimension
        case 11:
            return UITableViewAutomaticDimension
        default:
            return (self.ad.tamplate_id.intValue == indexPath.section) ? UITableViewAutomaticDimension : 0
        }
    }
    
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return self.ad.images.count
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "cell", for: indexPath) as! CommericalCell
        
        cell.im = self.ad.images[indexPath.row]
        
        if collectionView.tag != 2{
            if indexPath.row == self.selectedIndex{
                cell.layer.borderColor = Theme.Color.red.cgColor
                cell.layer.borderWidth = 3
            }else{
                cell.layer.borderWidth = 0
            }
            
        }else{
            cell.layer.borderWidth = 0
        }
        
        return cell
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        let h = collectionView.frame.height - 20
        if collectionView.tag == 2{
            return CGSize.init(width: collectionView.frame.width, height: collectionView.frame.height)
        }
        return CGSize.init(width: h, height: h)
    }
    
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        
        if collectionView.tag == 2{
            
            var images  = [LightboxImage]()
            
            for i in self.ad.images{
                if let urlString = i.image, let url = URL.init(string: Communication.shared.baseImgsURL + urlString){
                    let im = LightboxImage.init(imageURL: url)
                    images.append(im)
                }
            }
            
            /*for i in 0..<self.ad.images.count{
                if let j =  collectionView.cellForItem(at: IndexPath.init(item: i, section: 0)){
                    if let cc = j as? CommericalCell{
                        if let m = cc.img.image{
                            let im = LightboxImage.init(image: m)
                            images.append(im)
                        }
                    }
                }
            }*/

            
            self.imagesController = LightboxController(images: images)
            self.imagesController.dynamicBackground = true
            
            self.imagesController.headerView.deleteButton.isHidden = false
            self.imagesController.headerView.deleteButton.isEnabled = true
            self.imagesController.headerView.deleteButton.setImage(#imageLiteral(resourceName: "share"), for: .normal)

            self.imagesController.headerView.deleteButton.addTarget(self, action: #selector(shareImage), for: .touchUpInside)
            
            present(self.imagesController, animated: true, completion: nil)
            if indexPath.row < images.count{
                self.imagesController.goTo(indexPath.item)
            }
            
        }else {
            self.collectionView2.scrollToItem(at: indexPath, at: .centeredHorizontally, animated: true)
            self.selectedIndex = indexPath.row
            self.collectionView.reloadSections(IndexSet.init(integer: IndexSet.Element.init(0)))
            //            self.collectionView.reloadData()
        }
        //        Provider.sd_setImage(self.img, urlString: self.ad.images[indexPath.row].image)
    }
    
    @objc func shareImage(){
        
        let imgV = UIImageView()
        
        Provider.sd_setImage(imgV, urlString: self.ad.images[self.imagesController.currentPage].image)
        
            // set up activity view controller
            let imageToShare = [ imgV.image! ]
            let activityViewController = UIActivityViewController(activityItems: imageToShare, applicationActivities: nil)
            activityViewController.popoverPresentationController?.sourceView = self.view // so that iPads won't crash
            
            // exclude some activity types from the list (optional)
            activityViewController.excludedActivityTypes = [  ]
        //UIActivityType.airDrop, UIActivityType.postToFacebook
            
            // present the view controller
            self.imagesController.present(activityViewController, animated: true, completion: nil)
        
    }
    
    
    //Delegate With ScrollView (scroll item photos)
    override func scrollViewDidScroll(_ scrollView: UIScrollView) {
        if scrollView == self.collectionView2{
            if self.ad.images.count > 0 {
                if self.collectionView2.visibleCells.count > 0{
                    let pp = round(self.collectionView2.contentOffset.x / self.collectionView2.frame.size.width)
                    self.selectedIndex = Int(pp)
                    self.collectionView.reloadData()
                }
            }
        }
    }
    
    
    override func tableView(_ tableView: UITableView, titleForHeaderInSection section: Int) -> String? {
        return nil
    }
    
    
}

